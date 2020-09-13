package part5_advanced

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object CustomOperators extends App {
  implicit val system = ActorSystem("CustomOperators")
  implicit val materializer = ActorMaterializer()


}
