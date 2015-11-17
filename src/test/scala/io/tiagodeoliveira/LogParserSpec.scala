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

}
