package net.cucumbersome
import eu.timepit.refined._
import eu.timepit.refined.string._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.collection.NonEmpty

package object feedMine {
  type NonEmptyString = String Refined NonEmpty
  type StringedUrl = String Refined Url
}
