package io.tiagodeoliveira

import org.json4s.JsonAST.{JField, JObject}
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods

import scala.collection.mutable.ArrayBuffer

/**
  * This class holds the all game representation data.
  * It's also resposible for consolidating the game data.
  *
  * Created by tiagooliveira on 11/18/15.
  */
case class Player(name: String)
case class Weapon(name: String)
case class Kill(killer: Player, killed: Player, weapon: Weapon)

class Game(val id: Int, var totalKills: Int, val kills: ArrayBuffer[Kill]) {

  private val MASTER_PLAYER = "<world>"

  override def toString = {
    JsonMethods.compact(JsonMethods.render(this.getGameAsJson()))
  }

  def simpleReport() = {
    val json = this.getGameAsJson()
    JsonMethods.compact(JsonMethods.render(json.removeField {
      _._1 == "players"
    }))
  }

  /**
    * Returns a json object with the report with kills by mean.
    * */
  def killsByMeanReport() = {
    val killByMeans = kills.filter(_.killer != MASTER_PLAYER).map(_.weapon).groupBy(identity).mapValues(_.size)

    var json: JObject =
      (s"game_$id" ->
        ("kills_by_means" -> killByMeans.map { mean =>
          ((mean._1.name) -> mean._2)
        })
        )

    JsonMethods.compact(JsonMethods.render(json))
  }

  /**
    * Returns the game object as a json, executing the data consolidation on the calculated fields.
    * */
  def getGameAsJson() = {
    val validKills = this.getOnlyValidKills

    var json: JObject =
      (s"game_$id" ->
        ("total_kills" -> totalKills) ~
          ("players" -> validKills.flatMap(e => List(e.killed, e.killer)).groupBy(_.name).map(_._1)) ~
          ("kills" -> validKills.groupBy(_.killer.name).map { kill =>
            ((kill._1) -> getKillsByPlayer(kill._1))
          })
        )

    json
  }

  /**
    * Returns only the valid kills, disregarding the kills when killer is MASTER_PLAYER
    * */
  private def getOnlyValidKills = {
    kills.filter(_.killer.name != MASTER_PLAYER)
  }

  /**
    * Accumulates the amount of kills per player.
    * Whenever player kills himself of is killed by MASTER_PLAYER, it acummulates -1 kill.
    * */
  private def getKillsByPlayer(player: String) = {
    val killedByAdmin: Int = this.kills.count(kill => (kill.killer.name == MASTER_PLAYER || kill.killer.name == player) && (kill.killed.name == player))
    val kills: Int = this.kills.count(kill => kill.killer.name == player)

    kills - killedByAdmin
  }
}
