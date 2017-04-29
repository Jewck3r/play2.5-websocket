package models

import play.api.libs.json._
import play.api.mvc.WebSocket._

sealed trait InEvent

case class Note(user: String, content: String) extends InEvent

object Note {
	implicit val jsonFormat = Json.format[Note]
}

object InEvent {
  implicit def InEventFormat: Format[InEvent] = Format(
    (__ \ "event").read[String].flatMap {
      case "note" => Note.jsonFormat.map(identity)
      case other => Reads(_ => JsError("Unknown client event: " + other))
    },
    Writes {
      case event: Note => Note.jsonFormat.writes(event)
    })
}