package io.tiagodeoliveira

import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by tiagooliveira on 11/17/15.
  */
class LogParserSpec extends FunSuite with BeforeAndAfter {

  var logParser: LogParser = _
  var logFileName = "src/test/resources/games.log"

  before {
    logParser = new LogParser
  }

  test("If the method returns an game array") {
    val result = logParser.parseFile(logFileName)
    assert(result != null)
  }

  test("parser returns the correct amount of games from the log file") {
    val expectedGamesAmount = 21

    val result = logParser.parseFile(logFileName)
    assertResult(expectedGamesAmount)(result.size)
  }

  test("returns the correct amount of kills per game") {
    val expectedGamesKillCounts = Map(1 -> 0, 2 -> 11, 3 -> 4, 4 -> 105, 5 -> 14, 19 -> 95, 20 -> 3, 21 -> 131)

    val result = logParser.parseFile(logFileName)
    result.foreach { game =>
      if (expectedGamesKillCounts contains game.id) {
        assertResult(expectedGamesKillCounts(game.id))(game.killCount)
      }
    }
  }

  test("return the correct amount of game kills history") {
    val expectedGamesAmount = 131

    val result = logParser.parseFile(logFileName)
    assertResult(expectedGamesAmount)(result.last.killHistory.size)
  }

  test("return the correct game kills history for a given game") {
    val result = logParser.parseFile(logFileName)
    assertResult(new Kill("Dono da Bola", "Isgalamido", "MOD_ROCKET"))(result.last.killHistory.head)
  }

}
