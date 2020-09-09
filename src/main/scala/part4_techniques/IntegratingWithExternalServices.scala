package part4_techniques

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object IntegratingWithExternalServices extends App {

  implicit val system = ActorSystem("IntegratingWithExternalServices")
  implicit val materializer = ActorMaterializer()





}
