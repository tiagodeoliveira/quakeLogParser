package io.tiagodeoliveira

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by tiagooliveira on 11/17/15.
  */
class LogParser {

  case class Game(id: Long, var kills: Int)

  def parseFile(filename: String): ArrayBuffer[Game] = {

    val games: ArrayBuffer[Game] = new ArrayBuffer[Game]
    var gameId: Long = 0

    for (line <- Source.fromFile(filename).getLines()) {
      if (isANewGame(line)) {
        gameId += 1
        games += new Game(gameId, 0)
      } else if (isAKill(line)) {
        games.last.kills += 1
      }
    }

    return games
  }

  private def isANewGame(line: String): Boolean = {
    line.contains("InitGame:")
  }

  private def isAKill(line: String): Boolean = {
    line.contains("Kill:")
  }
}
