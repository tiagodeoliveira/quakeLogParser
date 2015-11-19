package io.tiagodeoliveira

import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods

import scala.collection.mutable.ArrayBuffer

/**
 * Created by tiagooliveira on 11/18/15.
 */
case class Player(name: String)

case class Weapon(name: String)

case class Kill(killer: Player, killed: Player, weapon: Weapon)

class Game(val id: Int, var totalKills: Int, val kills: ArrayBuffer[Kill]) {

  private val MASTER_PLAYER = "<world>"

  override def toString = {
    JsonMethods.compact(this.getGameAsJson())
  }

  def simpleReport() = {
    val jsonGame = this.getGameAsJson()
    jsonGame.::("players")
  }

  private def getGameAsJson() = {
    val validKills = this.getOnlyValidKills

    val json =
      (s"game_$id" ->
        ("total_kills" -> totalKills) ~
          ("players" -> validKills.map(_._1)) ~
          ("kills" -> validKills.map { kill =>
            ((kill._1) -> getKillsByPlayer(kill._1))
          })
        )

    JsonMethods.render(json)
  }

  private def getOnlyValidKills = {
    kills.groupBy(_.killer.name).filter(_._1 != MASTER_PLAYER)
  }

  private def getKillsByPlayer(player: String) = {
    kills.count(kill => kill.killer.name == MASTER_PLAYER && kill.killed.name == player)
  }
}
