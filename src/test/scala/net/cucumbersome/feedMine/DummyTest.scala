package net.cucumbersome.feedMine

import net.cucumbersome.feedMine.test.UnitTest
import cats.implicits._

class DummyTest extends UnitTest{
  "Dummy test" when {
    "running it" should {
      "pass" in {
        1 + 1 should === (2)
      }
    }
  }
}
