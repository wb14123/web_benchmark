package me.binwang.web_benchmark

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import redis.{RedisServer, RedisClientPool}

object Config {

  val config = ConfigFactory.load()

  implicit val akkaSystem = ActorSystem("web-benchmark")

  val futureDispatcher = config.getString("future-dispatcher")
  val executionContext = Config.akkaSystem.dispatchers.lookup(futureDispatcher)


  val serverPort = config.getInt("server.port")

  val redisHost = config.getString("redis.host")
  val redisPort = config.getInt("redis.port")
  val redisPoolSize = config.getInt("redis.pool-size")

  val redisClient: RedisClientPool = new RedisClientPool(
    (0 to redisPoolSize).map(n => RedisServer(redisHost, redisPort)))


}
