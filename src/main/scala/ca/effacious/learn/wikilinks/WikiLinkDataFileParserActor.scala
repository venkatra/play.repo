package ca.effacious.learn.wikilinks

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import akka.event.Logging.InitializeLogger
import ca.effacious.learn.meetup.{DispatchEventMessage, RSVPEventMessageParser, StartListeningToStream, StopListeningToStream}
import com.github.andyglow.websocket.WebsocketClient

import scala.io.Source

/**
  * Used for parsing the wiki links dataset.
  *
  */
class WikiLinkDataFileParserActor extends Actor with akka.actor.ActorLogging {

  private val DATA_FILE_PATH="/Users/d3vl0p3r/Downloads/wiki/wiki_links.txt" //ideally should be pulled from configuration file

  var c_links_parsed :Long = 0

  /**
    * Actor to forward stats too.
    */
  private var app_actor_ref: Option[ActorRef] = None

  def receive = {
    case msg :InitializeLogger => {
      sender ! (Logging.loggerInitialized(),self)
    }
    case msg :StartParsingEventMessage => start_parsing

//    case msg :RouteAndDispatchEventMessage => {
//      log.debug(" ..." )
//      route_and_dispatch(msg)
//    }
//
//    case msg :RecordURL => {
//      //do nothing now
//    }

    case x => println("--received unknown message : " + x.toString)
  }

  def route_and_dispatch(p_msg :RouteAndDispatchEventMessage): Unit = {
    //println(s"<<| ${msg.rsvp_event} ")
   //p_msg.line)
    //extract rsvp id
    //    val rsvp_id = c_message_parser.parse(msg.rsvp_event).get_field("rsvp_id").get.toString
    //
    //    val rsvp = RsvpEvent(msg.event_count_no ,rsvp_id ,msg.rsvp_event)
    //    log.debug("client -rsvp {}/{} -> location_actor" ,msg.event_count_no ,rsvp_id)
    //
    //
    //
    //    context.actorOf(Props[LocationConcernActor]) ! rsvp

    //return event that was processed to caller
  }




  def start_parsing() = {
    log.info("Starting to parse data file ({}) ...",DATA_FILE_PATH)

    app_actor_ref = Some(sender) // save reference to process invoker
    val mentions_actor = context.actorOf(Props[RouteAndDispatchActor])
    val tokens_actor = context.actorOf(Props[RouteAndDispatchActor])
    val url_actor = context.actorOf(Props[RouteAndDispatchActor])

    /**
      Sample Record :
      URL     ftp://212.18.29.48/ftp/pub/allnet/nas/all60300/ALL60300_UM_V12_EN.pdf
      MENTION r NetBIOS       176937  http://en.wikipedia.org/wiki/NetBIOS
      TOKEN   acting  304940
      TOKEN   whose   247626
      TOKEN   capabilities    70039
      TOKEN   ME      201398
      TOKEN   calls   514390
      TOKEN   preferably      346689
      TOKEN   functionality   358183
      TOKEN   anything        7034
      TOKEN   boost   508294
      TOKEN   enjoying        211878

      */
    var current_url = ""
    var route_msg = new RouteAndDispatchEventMessage(current_url ,"")

    Source.fromFile(DATA_FILE_PATH).getLines().filter(x => {(x.trim.length > 0) && (c_links_parsed < 2000000)} ).foldLeft(0) {
      (i,ln) => {
        if(ln.startsWith("URL")) {
          current_url = ln.substring(3).replaceAll("\t","").trim()
          c_links_parsed = c_links_parsed + 1
          url_actor ! new RecordURL(current_url)
          log.debug("=> {} {}",c_links_parsed,current_url)

        } else {

          ln match {
            case x:String if(x.startsWith("TOKEN")) => tokens_actor ! route_msg.copy(link_url = current_url ,line = ln)
            case x:String if(x.startsWith("MENTION")) => mentions_actor ! route_msg.copy(link_url = current_url ,line = ln)
          }

        }
        i+1
      }
    }

    log.debug("Completed parsing the file!!!")
  }


}
