package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import actors._
import models._
import akka.stream.Materializer
import play.api.libs.streams._
import play.api.mvc.WebSocket._
import akka.actor._

@Singleton
class SocketController @Inject() (implicit system: ActorSystem, materializer: Materializer) extends Controller {

  implicit val messageFlowTransformer = MessageFlowTransformer.jsonMessageFlowTransformer[InEvent, OutEvent]

  def animatorSocket = WebSocket.accept[InEvent, OutEvent] { request =>
    ActorFlow.actorRef(out => AnimatorSocket.props(out))
  }

}