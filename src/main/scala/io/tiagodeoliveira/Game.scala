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

  override def toString = {
    val json =
      (s"game_$id" ->
        ("total_kills" -> totalKills) ~
          ("players" -> kills.groupBy(_.killer.name).map(_._1)) ~
          ("kills" -> kills.groupBy(_.killer.name).map { kill =>
            ((kill._1) -> kill._2.size)
          })
        )

    JsonMethods.compact(JsonMethods.render(json))
  }

}
