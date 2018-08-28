package net.cucumbersome.cynara

import net.cucumbersome.cynara.test.UnitTest
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
