package ca.effacious.learn.wikilinks

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import akka.event.Logging.InitializeLogger


class RouteAndDispatchActor extends Actor with akka.actor.ActorLogging {


  def receive = {
    case msg :RouteAndDispatchEventMessage => route_and_dispatch(msg)

    case msg :RecordURL => {
      //do nothing now
    }

    case x => println("<< received unknown message : " + x.toString)
  }

  def route_and_dispatch(p_msg :RouteAndDispatchEventMessage): Unit = {
    //println(s"<<| ${msg.rsvp_event} ")
    //println(p_msg.line)
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

}
