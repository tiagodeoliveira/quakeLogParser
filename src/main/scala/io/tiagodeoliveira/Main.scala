package io.tiagodeoliveira

import scala.collection.mutable.ArrayBuffer

/**
  * Main class in order to run the program as a executable.
  *
  * Created by tiagooliveira on 11/19/15.
  */
object Main {
  def main(args: Array[String]) {
    val games: ArrayBuffer[Game] = new LogParser().parseFile("src/test/resources/all_games.log")
    games.foreach { game =>
      println("===================================================")
      println(game.toString())
      println(game.simpleReport())
      println(game.killsByMeanReport())
    }
  }
}
