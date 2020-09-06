package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object GraphMaterializedValues extends App {

  implicit val system = ActorSystem("GraphMaterializedValues")
  implicit val materializer = ActorMaterializer()

}
