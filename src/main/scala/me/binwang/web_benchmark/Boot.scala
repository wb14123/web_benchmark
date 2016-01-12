package me.binwang.web_benchmark

import akka.actor.Props
import akka.io.IO
import akka.util.Timeout
import spray.can.Http
import akka.pattern.ask
import scala.concurrent.duration._

object Boot extends App {

  implicit val system = Config.akkaSystem
  implicit val timeout = Timeout(5.seconds)

  val service = system.actorOf(Props[Service].withDispatcher(Config.futureDispatcher), "web-benchmark")

  val host = "0.0.0.0"
  val port = Config.serverPort
  IO(Http) ? Http.Bind(service, interface = host, port = port, backlog = 2048)

  println(s"Server started at $host:$port")

}
