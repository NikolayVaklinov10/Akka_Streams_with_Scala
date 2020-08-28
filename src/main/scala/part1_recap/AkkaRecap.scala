package part1_recap

import akka.actor.{Actor, ActorSystem, Props, Stash}

object AkkaRecap extends App {

  class SimpleActor extends Actor with Stash {
    def receive: Receive = {
          // STASHING
      case "stashThis" =>
        stash()
      case "change handler NOW" =>
        unstashAll()
        context.become(anotherHandler)
      case "change" => context.become(anotherHandler)
      case message => println(s"I have received: $message")
    }
    def anotherHandler: Receive = {
      case message => println(s"In another receive handler: $message")
    }
  }

  val system = ActorSystem("AkkaRecapExample")
  val myActor = system.actorOf(Props[SimpleActor], "myActor")
  myActor ! "an email"

}
