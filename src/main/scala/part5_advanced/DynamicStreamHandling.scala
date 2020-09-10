package part5_advanced

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object DynamicStreamHandling extends App {

  implicit val system = ActorSystem("DynamicStreamHandling")
  implicit val materializer = ActorMaterializer()



}
