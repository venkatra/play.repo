package ca.effacious.learn.meetup

import akka.actor.{ActorSystem, Props}
import org.slf4j.LoggerFactory

/**
  * The application class for the meetup rsvp streams
  */
object RSVPStreamLoggerApp extends App  {
  private val logger = LoggerFactory.getLogger(classOf[RSVPStreamClientActor])

  import akka.util.Timeout
  import scala.concurrent.duration._
  import akka.pattern.ask
  import akka.dispatch.ExecutionContexts._

  implicit val ec = global

  override def main(args: Array[String]) {
    val system = ActorSystem("System")
    val actor = system.actorOf(Props(new RSVPStreamClientActor ))
    actor ! new StartListeningToStream()

  }

}

