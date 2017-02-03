package ca.effacious.learn.meetup


import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import akka.event.Logging.InitializeLogger
import com.github.andyglow.websocket.WebsocketClient


/**
  * Listens to Meetup RSVP stream (using web sockets) and passes this information
  * over to different actors.
  *
  */
class RSVPStreamClientActor extends Actor with akka.actor.ActorLogging {

  private val RSVP_STREAM_URL="ws://stream.meetup.com/2/rsvps" //ideally should be pulled from configuration file
  private var wscli :Option[WebsocketClient[String]] = None

  /**
    * Actor to forward stats too.
    */
  private var app_actor_ref: Option[ActorRef] = None

  private val c_message_parser = new RSVPEventMessageParser

  //variables used for status
  private var countof_rsvp_received :Long = 0
  private var start_time :String = ""


  def receive = {
    case msg :InitializeLogger => {
      sender ! (Logging.loggerInitialized(),self)
    }
    case msg :StartListeningToStream => wscli = start_listening

    case msg :StopListeningToStream => stop_listening

    case msg :DispatchEventMessage => dispatch_message(msg)

    case _ => println("received unknown message")
  }

  def dispatch_message(msg :DispatchEventMessage): Unit = {
    //    println(msg.rsvp_event)
    //extract rsvp id
    val rsvp_id = c_message_parser.parse(msg.rsvp_event).get_field("rsvp_id").get.toString

    val rsvp = RsvpEvent(msg.event_count_no ,rsvp_id ,msg.rsvp_event)
    log.debug("client -rsvp {}/{} -> location_actor" ,msg.event_count_no ,rsvp_id)

    context.actorOf(Props[LocationConcernActor]) ! rsvp

    //return event that was processed to caller
  }

  def start_listening() = {
    log.info("Starting to listen to rsvp stream ({}) ...",RSVP_STREAM_URL)

    app_actor_ref = Some(sender) // save reference to process invoker

    // 1. prepare ws-client
    // 2. define message handler
    val cli = WebsocketClient[String](RSVP_STREAM_URL) {
      case rsvp_event:String => {
        //update received count
        countof_rsvp_received = countof_rsvp_received + 1
        //send to dispatcher
        self ! new DispatchEventMessage(countof_rsvp_received ,rsvp_event)
        //nice to have receive future on messages that were dispatched
      }

    }

    // 4. open websocket
    cli.open()
    Some(cli)
  }

 def stop_listening() = {
   log.info("Closing connection with stream ...")
   wscli.foreach( _.shutdownSync())
 }

}

