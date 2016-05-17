package me.binwang.web_benchmark

import akka.actor.Actor
import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport
import spray.routing.HttpService

class Service extends Actor with HttpService with Json4sSupport {

  implicit def json4sFormats: Formats = DefaultFormats.withDouble.withLong
  implicit val executionContext = Config.executionContext

  def actorRefFactory = context
  def receive = runRoute(route)

  val route =
    path("get") {
      get {
        parameters('key) { key =>
          val result = Config.redisClient.get[String](key).map{value => Map(key -> value)}
          complete(result)
        }
      }
    } ~ path ("set") {
      get {
        parameter('key, 'value) { (key, value) =>
          complete(Config.redisClient.set[String](key, value).map{_ => Map(key -> value)})
        }
      }
    } ~ path ("none") {
      get {
        complete(Map("status" -> "ok"))
      }
    }
}
