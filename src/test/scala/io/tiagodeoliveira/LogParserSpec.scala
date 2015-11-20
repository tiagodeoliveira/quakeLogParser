package io.tiagodeoliveira

import org.scalatest.{BeforeAndAfter, FunSpec}

/**
 * Created by tiagooliveira on 11/17/15.
 */
class LogParserSpec extends FunSpec with BeforeAndAfter {

  var logParser: LogParser = _
  var singleGameFileName = "src/test/resources/single_game.log"
  var allGamesFileName = "src/test/resources/all_games.log"

  before {
    logParser = new LogParser
  }

  describe("A set of tests with only one game") {
    it("should return a valid result array") {
      val result = logParser.parseFile(singleGameFileName)
      assert(result != null)
    }

    it("shoulld return the correct amount of game kills history") {
      val expectedGamesAmount = 131

      val result = logParser.parseFile(singleGameFileName)
      assertResult(expectedGamesAmount)(result.last.kills.size)
    }

    it("should return the correct game kills history for a given game") {
      val result = logParser.parseFile(singleGameFileName)
      assertResult(new Kill(new Player("Dono da Bola"), new Player("Isgalamido"), new Weapon("MOD_ROCKET")))(result.last.kills.head)
    }

    it(" should return the game as the output json") {
      val expectedJsonOutput = """{"game_1":{"total_kills":131,"players":["Dono da Bola","Assasinu Credi","Oootsimo","Zeh","Mal","Isgalamido"],"kills":{"Dono da Bola":2,"Assasinu Credi":3,"Oootsimo":2,"Zeh":2,"Mal":6,"Isgalamido":2}}}"""

      val result = logParser.parseFile(singleGameFileName)
      assertResult(expectedJsonOutput)(result.last.toString)
    }
  }

  describe("A set of tests with many games on a file") {
    it("should return the right amount of games on the log and the sum of all kills") {
      val expectedGamesAmount = 21
      val expectedAmountOfKills = 1069

      val result = logParser.parseFile(allGamesFileName)
      assertResult(expectedGamesAmount)(result.size)
      assertResult(expectedAmountOfKills)(result.foldLeft(0){ _ + _.totalKills})
    }

    it("it should return the correct kill amounts from the given games") {
      val expectedGamesKillCounts = Map(1 -> 0, 2 -> 11, 3 -> 4, 4 -> 105, 5 -> 14, 19 -> 95, 20 -> 3, 21 -> 131)

      val result = logParser.parseFile(allGamesFileName)
      result.foreach { game =>
        if (expectedGamesKillCounts contains game.id) {
          assertResult(expectedGamesKillCounts(game.id))(game.totalKills)
        }
      }
    }
  }

  describe("Test game reports from a single match") {
    it("should return a simple game report") {
      val expectedJsonOutput = """{"game_1":{"total_kills":131,"kills":{"Dono da Bola":2,"Assasinu Credi":3,"Oootsimo":2,"Zeh":2,"Mal":6,"Isgalamido":2}}}"""

      val result = logParser.parseFile(singleGameFileName)
      assertResult(expectedJsonOutput)(result.last.simpleReport())
    }

    it("should return a kills by mean report") {
      val expectedJsonOutput = """{"game_1":{"kills_by_means":{"MOD_ROCKET":37,"MOD_SHOTGUN":4,"MOD_ROCKET_SPLASH":60,"MOD_RAILGUN":9,"MOD_TRIGGER_HURT":14,"MOD_MACHINEGUN":4,"MOD_FALLING":3}}}"""

      val result = logParser.parseFile(singleGameFileName)
      assertResult(expectedJsonOutput)(result.last.killsByMeanReport())
    }
  }
}
