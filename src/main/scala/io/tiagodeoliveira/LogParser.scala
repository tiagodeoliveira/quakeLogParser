package io.tiagodeoliveira

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by tiagooliveira on 11/17/15.
  */

case class Kill(killer: String, killed: String, weapon: String)
case class Game(id: Int, var killCount: Int, killHistory: ArrayBuffer[Kill])

class LogParser {


  def parseFile(filename: String): ArrayBuffer[Game] = {

    val games: ArrayBuffer[Game] = new ArrayBuffer[Game]
    var gameId: Int = 0

    for (line <- Source.fromFile(filename).getLines()) {
      if (isANewGame(line)) {
        gameId += 1
        games += new Game(gameId, 0, new ArrayBuffer[Kill]())
      } else if (isAKill(line)) {
        games.last.killCount += 1
        games.last.killHistory += getKillInfo(line)
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

  private def getKillInfo(line: String): Kill = {
    val KillExtractor(killer, killed, weapon) = line
    new Kill(killer, killed, weapon)
  }

}
