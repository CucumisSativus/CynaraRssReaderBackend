name := "FeedMineBackend"

version := "0.1"

scalaVersion := "2.12.6"

val http4sVersion = "0.18.16"
val doobieVersion ="0.5.3"
val circeVersion = "0.9.3"
val scalatestVersion = "3.0.5"

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.4.2",
  "org.typelevel" %% "cats-core" % "1.2.0",
  "org.typelevel" %% "cats-testkit" % "1.2.0",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
  "org.tpolecat" %% "doobie-scalatest" % doobieVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "co.fs2" %% "fs2-core" % "0.10.4",
  "com.rometools" % "rome" % "1.11.0",
  "io.monix" %% "monix" % "3.0.0-RC1",
  "com.github.pureconfig" %% "pureconfig" % "0.9.2",
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha4",
  "org.typelevel" %% "kittens" % "1.1.0",
  "eu.timepit" %% "refined" % "0.9.2",
  "com.softwaremill.common" %% "tagging" % "2.2.1",
  "com.danielasfregola" %% "random-data-generator" % "2.5"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "utf-8",
  "-explaintypes",
  "-feature",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xcheckinit",
  "-Xfuture",
  "-Xlint:adapted-args",
  "-Xlint:by-name-right-associative",
  "-Xlint:constant",
  "-Xlint:delayedinit-select",
  "-Xlint:doc-detached",
  "-Xlint:inaccessible",
  "-Xlint:infer-any",
  "-Xlint:missing-interpolator",
  "-Xlint:nullary-override",
  "-Xlint:nullary-unit",
  "-Xlint:option-implicit",
  "-Xlint:package-object-classes",
  "-Xlint:poly-implicit-overload",
  "-Xlint:private-shadow",
  "-Xlint:stars-align",
  "-Xlint:type-parameter-shadow",
  "-Xlint:unsound-match",
  "-Yno-adapted-args",
  "-Ypartial-unification",
  "-Ywarn-dead-code",
  "-Ywarn-extra-implicit",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused:implicits",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:locals",
  "-Ywarn-unused:params",
  "-Ywarn-unused:patvars",
  "-Ywarn-unused:privates",
  "-Ywarn-value-discard"
)
