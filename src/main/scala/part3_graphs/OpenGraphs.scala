package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object OpenGraphs extends App {

  implicit val system = ActorSystem("OpenGraphs")
  implicit val materializer = ActorMaterializer()

  /*
    A composite source that concatenates 2 sources
    - emits ALL the elements from the first source
    - then ALL the elements from the second one
   */

}
