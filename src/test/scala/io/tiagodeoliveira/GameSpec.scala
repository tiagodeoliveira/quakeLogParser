package io.tiagodeoliveira

import org.json4s.JsonAST.JInt
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

/**
 * Created by tiago on 19/11/15.
 */
class GameSpec extends FunSuite {

  test("if the json has the expected kills amount by player") {
    val kills = new ArrayBuffer[Kill]()
    kills += new Kill(new Player("john"), new Player("petter"), new Weapon("shotgun"))
    kills += new Kill(new Player("john"), new Player("mark"), new Weapon("shotgun"))
    kills += new Kill(new Player("mary"), new Player("bill"), new Weapon("shotgun"))

    val game = new Game(1, 1, kills)
    val killsResult = (game.getGameAsJson() \\ "kills")
    assertResult(new JInt(1))((killsResult \\ "mary"))
    assertResult(new JInt(2))((killsResult \\ "john"))
  }

  test("if the json has the expected kills amount by player with kills by master player") {
    val kills = new ArrayBuffer[Kill]()
    kills += new Kill(new Player("<world>"), new Player("petter"), new Weapon("shotgun"))
    kills += new Kill(new Player("petter"), new Player("mark"), new Weapon("shotgun"))
    kills += new Kill(new Player("mark"), new Player("petter"), new Weapon("shotgun"))

    val game = new Game(1, 1, kills)
    val killsResult = (game.getGameAsJson() \\ "kills")
    assertResult(new JInt(0), "peter does not have the expected amount of kills")((killsResult \\ "petter"))
    assertResult(new JInt(1), "mark does not have the expected amount of kills")((killsResult \\ "mark"))
  }

  test("if the json has the expected kills amount by player when player kills himself") {
    val kills = new ArrayBuffer[Kill]()
    kills += new Kill(new Player("mark"), new Player("petter"), new Weapon("shotgun"))
    kills += new Kill(new Player("petter"), new Player("petter"), new Weapon("shotgun"))
    kills += new Kill(new Player("mark"), new Player("petter"), new Weapon("shotgun"))

    val game = new Game(1, 1, kills)
    val killsResult = (game.getGameAsJson() \\ "kills")
    assertResult(new JInt(0), "petter does not have the expected amount of kills")((killsResult \\ "petter"))
    assertResult(new JInt(2), "mark does not have the expected amount of kills")((killsResult \\ "mark"))
  }
}
