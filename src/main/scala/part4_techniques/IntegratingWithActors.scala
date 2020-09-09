package part4_techniques

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.Timeout

import scala.concurrent.duration._

object IntegratingWithActors extends App {

  implicit val system = ActorSystem("IntegratingWithActors")
  implicit val materializer = ActorMaterializer()

  class SimpleActor extends Actor with ActorLogging {

    override def receive: Receive = {
      case s: String =>
        log.info(s"Just received a string: $s")
        sender() ! s"$s$s"
      case n: Int =>
        log.info(s"Just received a number: $n")
        sender() ! (2 * n)
      case _ =>
    }
  }

  val simpleActor = system.actorOf(Props[SimpleActor],"simpleActor")

  val numbersSource = Source(1 to 10)

  // actor as a flow
  implicit val timeout = Timeout(2 second)
  val actorBasedFlow = Flow[Int].ask[Int](parallelism = 4)(simpleActor)

  // plugin everything together
//  numbersSource.via(actorBasedFlow).to(Sink.ignore).run()
//  // now exactly the same thing written in another way
//  numbersSource.ask[Int](parallelism = 4)(simpleActor).to(Sink.ignore).run()

  /*
    Actor as a source
   */
  val actorPoweredSource = Source.actorRef[Int](bufferSize = 10, overflowStrategy = OverflowStrategy.dropHead)
  val materializedActorRef = actorPoweredSource.to(Sink.foreach[Int](number => println(s"Actor powered flow got number: $number"))).run()
  materializedActorRef ! 10


}
