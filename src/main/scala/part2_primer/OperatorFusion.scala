package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object OperatorFusion extends App {

  implicit val system = ActorSystem("OperatorFusion")
  implicit val materializer = ActorMaterializer()

}
