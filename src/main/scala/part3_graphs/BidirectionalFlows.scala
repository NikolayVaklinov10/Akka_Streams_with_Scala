package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object BidirectionalFlows extends App {

  implicit val system = ActorSystem("BidirectionalFlows")
  implicit val materializer = ActorMaterializer()
  
}
