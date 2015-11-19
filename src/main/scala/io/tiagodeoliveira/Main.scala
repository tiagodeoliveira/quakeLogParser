package io.tiagodeoliveira

/**
  * Created by tiagooliveira on 11/19/15.
  */
object Main {
  def main(args: Array[String]) {
    val games = new LogParser().parseFile("src/test/resources/all_games.log")
    games.foreach { game =>
      println("===================================================")
      println(game.toString())
      println(game.simpleReport())
      println(game.killsByMeanReport())
    }
  }
}
