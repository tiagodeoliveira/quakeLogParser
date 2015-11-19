package io.tiagodeoliveira

import java.util.NoSuchElementException

import org.scalatest.{FunSuite, FunSpec}

/**
  * Created by tiagooliveira on 11/19/15.
  */
class KillExtractorSpec extends FunSuite {

  test("if the line extractor works well with a correct line") {
    val killer = "Assasinu Credi"
    val killed = "Oootsimo"
    val weapon = "MOD_ROCKET_SPLASH"
    val validKillLine = s"6:59 Kill: 5 3 7: $killer killed $killed by $weapon"

    val result = KillExtractor.unapplySeq(validKillLine)

    assertResult(killer)(result.get(0))
    assertResult(killed)(result.get(1))
    assertResult(weapon)(result.get(2))
  }

  test("if the line extractor raises an exception with a wrong line") {
    val validKillLine = "some random line that has nothing to do with all this context"

    val exception = intercept[NoSuchElementException] {
      KillExtractor.unapplySeq(validKillLine)
    }

    assert(exception != null)
  }
}
