package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object OpenGraphs extends App {

  implicit val system = ActorSystem("OpenGraphs")
  implicit val materializer = ActorMaterializer()

}
