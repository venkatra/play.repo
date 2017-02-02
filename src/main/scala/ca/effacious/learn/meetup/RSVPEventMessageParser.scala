package ca.effacious.learn.meetup

import spray.json._
import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)

/**
  * Used for parsing and extracting values of the RSVP event messages
  */
class RSVPEventMessageParser {

  var c_jsonAst :JsValue = _

  def parse(p_rsvp_event_msg :String) = {
    c_jsonAst = p_rsvp_event_msg.parseJson
    this
  }

  def get_field(p_fieldname :String):Option[JsValue] = {
    c_jsonAst.asJsObject.fields.get(p_fieldname)
  }

  def debug(): Unit = {
    c_jsonAst.prettyPrint
  }
}
