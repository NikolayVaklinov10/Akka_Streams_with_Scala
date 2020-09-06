package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object MoreOpenGraphs extends App {

  implicit val system = ActorSystem("MoreOpenGraphs")
  implicit val materializer = ActorMaterializer()

}
