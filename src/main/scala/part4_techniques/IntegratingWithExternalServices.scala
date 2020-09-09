package part4_techniques

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.Future

object IntegratingWithExternalServices extends App {

  implicit val system = ActorSystem("IntegratingWithExternalServices")
  implicit val materializer = ActorMaterializer()


  // external service may look like this
  def genericExtService[A, B](element: A): Future[B] = ???







}
