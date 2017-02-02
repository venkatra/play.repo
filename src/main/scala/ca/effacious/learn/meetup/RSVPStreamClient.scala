package ca.effacious.learn.meetup


import org.slf4j.LoggerFactory
import org.slf4j.Logger

/**
  * Listens to Meetup RSVP stream (using web sockets) and passes this information
  * over to different actors.
  *
  */
class RSVPStreamLogger {

  val logger = LoggerFactory.getLogger(classOf[RSVPStreamLogger])

  def main() = {
    logger.info("Hello World!!")
  }

}

object RSVPStreamLoggerMain extends App  {

  val p = new RSVPStreamLogger
  p.main

}

//-Dlog4j.configurationFile=file:/Users/d3vl0p3r/Dev/git_play_repo/akka_learn/src/main/resources/log4j.properties
//-Dlogback.configurationFile=/Users/d3vl0p3r/Dev/git_play_repo/akka_learn/src/main/resources/logback.xml