package part1_recap

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, ActorLogging, ActorSystem, OneForOneStrategy, Props, Stash, SupervisorStrategy}

object AkkaRecap extends App {

  class SimpleActor extends Actor with Stash with ActorLogging {
    def receive: Receive = {
            // SPAWN AN ACTOR
      case "createChild" =>
        val childActor = context.actorOf(Props[SimpleActor], "myChild")
        childActor ! "hello"
        log.info(s"I am also here $childActor")
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

    override def preStart(): Unit = {
      log.info("I am starting")
    }

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
      case _: RuntimeException => Restart
      case _: => Stop
    }
  }

  val system = ActorSystem("AkkaRecapExample")
  val myActor = system.actorOf(Props[SimpleActor], "myActor")
  myActor ! "an email"
  myActor ! "createChild"

}
