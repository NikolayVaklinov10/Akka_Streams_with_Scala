package part4_techniques

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object IntegratingWithActors extends App {

  implicit val system = ActorSystem("IntegratingWithActors")
  implicit val materializer = ActorMaterializer()

}
