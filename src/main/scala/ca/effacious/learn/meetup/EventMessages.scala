package ca.effacious.learn.meetup

case class StartListeningToStream()

case class StopListeningToStream()

case class DispatchEventMessage(event_count_no :Long, rsvp_event :String)

case class RsvpEvent(event_count_no :Long ,rsvp_id :String ,rsvp_event :String)

