package ca.effacious.learn.wikilinks

case class StartParsingEventMessage()

case class RouteAndDispatchEventMessage(link_url :String ,line :String)

case class RecordURL(link_url :String)



