package ca.effacious.learn.wikilinks

import akka.actor.{ActorSystem, Props}
import org.slf4j.LoggerFactory

/**
  * The application class for the parsing the wikilinks data and playing around actor system.
  *  Data Link: https://code.google.com/archive/p/wiki-links/downloads
  *
  */
object WikiLinksPlayaroundApp extends App  {
  private val logger = LoggerFactory.getLogger(classOf[WikiLinkDataFileParserActor])

  import akka.dispatch.ExecutionContexts._

  implicit val ec = global

  override def main(args: Array[String]) {
    val system = ActorSystem("System")
    val actor = system.actorOf(Props(new WikiLinkDataFileParserActor ))
    actor ! new StartParsingEventMessage()

  }


}

