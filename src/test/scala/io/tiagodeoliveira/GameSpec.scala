package io.tiagodeoliveira

import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by tiago on 19/11/15.
 */
class GameSpec extends FunSuite {

  test("if the json has the expected kills amount by player") {

    val kills = new ArrayBuffer[Kill]()
    kills += new Kill(new Player("john"), new Player("petter"), new Weapon("shotgun"))
    kills += new Kill(new Player("john"), new Player("john"), new Weapon("shotgun"))
    kills += new Kill(new Player("mary"), new Player("bill"), new Weapon("shotgun"))

    val game = new Game(1, 1, kills)
    val killsResult = (game.getGameAsJson() \\ "kills")
    assert(killsResult.children == null)
  }
}
