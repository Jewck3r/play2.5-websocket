package models

import play.api.libs.json._

sealed trait OutEvent

case class StatusEvent(content: String) extends OutEvent

object StatusEvent {
	implicit val jsonFormat = Json.format[StatusEvent]
}

object OutEvent {
  implicit def InEventFormat: Format[OutEvent] = Format(
    (__ \ "event").read[String].flatMap {
      case "status" => StatusEvent.jsonFormat.map(identity)
      case other => Reads(_ => JsError("Unknown client event: " + other))
    },
    Writes {
      case event: StatusEvent => StatusEvent.jsonFormat.writes(event)
    })
}