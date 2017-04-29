package actors

import akka.actor._
import models._

object AnimatorSocket {
  def props(out: ActorRef) = Props(new AnimatorSocket(out))
}

class AnimatorSocket(out: ActorRef) extends Actor {
  def receive = {
    case note: Note =>
      out ! StatusEvent(content = s"Receive the note : - ${note.content} - from user: ${note.user}")
  }
}