package net.cucumbersome.cynara.test

import org.scalatest.{Matchers, WordSpec}

abstract class UnitTest extends WordSpec with Matchers with cats.tests.StrictCatsEquality{

}
