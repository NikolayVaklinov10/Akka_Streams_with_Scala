package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object MaterializingStreams extends App {

  implicit val system = ActorSystem("MaterializingStreams")
  implicit val materializer = ActorMaterializer
  

}
