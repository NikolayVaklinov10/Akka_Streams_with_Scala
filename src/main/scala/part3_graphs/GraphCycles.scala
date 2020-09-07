package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object GraphCycles extends App {

  implicit val system = ActorSystem("GraphCycles")
  implicit val materializer = ActorMaterializer()

}
