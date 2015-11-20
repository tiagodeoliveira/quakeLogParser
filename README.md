## This is a very simple Quake-III-Arena log parser on Scala.

### The challenge: https://gist.github.com/akitaonrails/97310463c52467d2ecc6

#### How to build the project:

* First you need to install [Scala Build Tool](http://www.scala-sbt.org/release/tutorial/Setup.html)
* Now you can run the standard SBT tasks:
  * `sbt package`: to create the jar distribution;
  * `sbt test`: to run all the project unit tests and generate the output report;
  * `sbt run`: to run the main class that will print all the reports;;

### How to use:

```scala
val games: ArrayBuffer[Game] = new LogParser().parseFile("src/test/resources/all_games.log")
```

It will return an array with all the games in the game, every game has an ID that is the order it appears on the log file.

Every game inside the array can self generate all the necessary reports, using scala collections it makes really simple to calculate the necessary output result.

The JSON transformation is made using AST transformation from json4s framework.

### Why scala? Why that way?

Scala because it has a really performatic and powerful standard collections library, and it's possible to do virtually any transformation without any external library.

So, the solution was develop in order to extract the raw data and put inside an aggregator object, that has the capability to generate all the necessary transformations by itself.
