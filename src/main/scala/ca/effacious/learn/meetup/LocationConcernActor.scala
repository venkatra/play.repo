package ca.effacious.learn.meetup

import akka.actor.Actor
import akka.event.Logging

/**
  * Actor concerned with location information present in rsvp event messages.
  *
  */
class LocationConcernActor extends Actor with akka.actor.ActorLogging {

  def receive = {
    case rsvp_event :RsvpEvent => on_rsvp_message(rsvp_event)
    case _ => println("received unknown message")
  }

  def on_rsvp_message(rsvp_event :RsvpEvent) = {
    //log.debug("Received event ...")

    val message_parser = new RSVPEventMessageParser
    val group_obj = message_parser.parse(rsvp_event.rsvp_event).get_field("group")

    val gcity = group_obj.flatMap(_.asJsObject.fields.get("group_city")).getOrElse("")
    val gcountry = group_obj.flatMap(_.asJsObject.fields.get("group_country")).getOrElse("")
    log.debug(s" Loc [${rsvp_event.rsvp_id}] => ${gcity} @ ${gcountry}")


  }
}
