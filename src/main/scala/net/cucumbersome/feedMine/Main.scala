package net.cucumbersome.feedMine

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._

import scala.concurrent.ExecutionContext.Implicits.global
import fs2.{Stream, StreamApp}
import org.http4s.server.blaze.BlazeBuilder

object Main extends StreamApp[IO]{

  val helloWorldService: HttpService[IO] = HttpService[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, StreamApp.ExitCode] = {
    BlazeBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .mountService(helloWorldService, "/")
      .serve
  }
}
