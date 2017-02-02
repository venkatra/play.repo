import ca.effacious.learn.meetup.RSVPEventMessageParser
import spray.json.JsValue

val str =
  """ {"venue":{"venue_name":"london paddington train station","lon":-0.170354,"lat":51.509628,"venue_id":6920592},"visibility":"public","response":"yes","guests":0,"member":{"member_id":16259351,"other_services":{"twitter":{"identifier":"@topsecretlondon"}},"photo":"http:\/\/photos4.meetupstatic.com\/photos\/member\/c\/9\/9\/2\/thumb_105591602.jpeg","member_name":"Celeste"},"rsvp_id":1649269643,"mtime":1486041965175,"event":{"event_name":"\"Secret Berkshire\" The Hollies and the North Wessex Downs","event_id":"235801914","time":1486197900000,"event_url":"https:\/\/www.meetup.com\/GO-London\/events\/235801914\/"},"group":{"group_topics":[{"urlkey":"hiking","topic_name":"Hiking"},{"urlkey":"walkers","topic_name":"Walking"},{"urlkey":"travel","topic_name":"Travel"},{"urlkey":"outdoor-adventures","topic_name":"Outdoor Adventures"},{"urlkey":"fun-times","topic_name":"Fun Times"},{"urlkey":"adventure","topic_name":"Adventure"},{"urlkey":"outdoors","topic_name":"Outdoors"},{"urlkey":"hill-walking","topic_name":"Hill Walking"},{"urlkey":"trekking","topic_name":"Trekking"},{"urlkey":"weekend-adventures","topic_name":"Weekend Adventures"},{"urlkey":"social","topic_name":"Social"},{"urlkey":"photo","topic_name":"Photography"},{"urlkey":"mountains","topic_name":"Mountains"},{"urlkey":"exercize-and-wellness","topic_name":"Exercize and Wellness"},{"urlkey":"outdoor-fitness","topic_name":"Outdoor  Fitness"}],"group_city":"London","group_country":"gb","group_id":4662652,"group_name":"GO London, hiking, walking, outdoor, adventure","group_lon":-0.1,"group_urlname":"GO-London","group_lat":51.52}}

  """.stripMargin

var parserd = new RSVPEventMessageParser
parserd = parserd.parse(str)
val group_obj = parserd.get_field("group")


group_obj.isDefined
group_obj.isEmpty

val gcity = group_obj.flatMap(_.asJsObject.fields.get("group_city"))

println(s"Group : ${gcity.getOrElse("")}")

