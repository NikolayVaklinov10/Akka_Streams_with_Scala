package part4_techniques

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.Flow

object AdvancedBackPressure extends App {

  implicit val system = ActorSystem("AdvancedBackPressure")
  implicit val materializer = ActorMaterializer()

  // controlling backpressure
  val controlledFlow = Flow[Int].map(_ * 2).buffer(10, OverflowStrategy.dropHead)

}
