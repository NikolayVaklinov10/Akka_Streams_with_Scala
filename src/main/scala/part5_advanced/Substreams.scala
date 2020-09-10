package part5_advanced

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object Substreams extends App {

  implicit val system = ActorSystem("Substreams")
  implicit val materializer = ActorMaterializer()

}
