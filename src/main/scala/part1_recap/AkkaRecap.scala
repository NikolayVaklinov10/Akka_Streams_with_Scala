package part1_recap

import akka.actor.{Actor, ActorSystem, Props}

object AkkaRecap extends App {

  class SimpleActor extends Actor {
    def receive: Receive = {
      case message => println(s"I have received: $message")
    }
  }

  val system = ActorSystem("AkkaRecapExample")
  val myActor = system.actorOf(Props[SimpleActor], "myActor")
  myActor ! "an email"

}
