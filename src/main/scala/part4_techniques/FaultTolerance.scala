package part4_techniques

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object FaultTolerance extends App {

  implicit val system = ActorSystem("FaultTolerance")
  implicit val materializer = ActorMaterializer()

}
