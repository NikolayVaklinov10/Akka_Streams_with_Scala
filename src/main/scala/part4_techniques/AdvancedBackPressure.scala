package part4_techniques

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object AdvancedBackPressure extends App {

  implicit val system = ActorSystem("AdvancedBackPressure")
  implicit val materializer = ActorMaterializer()

}
