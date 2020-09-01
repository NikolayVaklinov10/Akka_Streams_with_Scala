package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object BackpressureBasics extends App {

  implicit val system = ActorSystem("BackPressureBasics")
  implicit val materializer = ActorMaterializer()

}
