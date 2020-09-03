package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object GraphBasics extends App {

  // the implicits for th code
  implicit val system = ActorSystem("GraphBasics")
  implicit val materializer = ActorMaterializer()


}
