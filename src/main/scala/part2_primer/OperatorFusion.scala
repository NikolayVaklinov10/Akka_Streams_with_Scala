package part2_primer

import akka.actor.{Actor, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object OperatorFusion extends App {

  implicit val system = ActorSystem("OperatorFusion")
  implicit val materializer = ActorMaterializer()

  val simpleSource = Source(1 to 1000) // 1. the source
  val simpleFlow = Flow[Int].map(_ + 1) // 2. the flow #1
  val simpleFlow2 = Flow[Int].map(_ * 10) // 2. the flow #2
  val simpleSink = Sink.foreach[Int](println) // 3. the sink

  // connecting all the components
//  simpleSource.via(simpleFlow).via(simpleFlow2).to(simpleSink).run() // a valid Akka Stream runable graph

  // Attention this run on the same Actor a.k.a. operator/component FUSION

  // "equivalent" behavior
  class SimpleActor extends Actor {
    override def receive: Receive = {
      case x: Int =>
        // flow operations
        val x2 = x + 1
        val y = x2 * 10
        // sink operation
        println(y)
    }
  }
  val simpleActor = system.actorOf(Props[SimpleActor])
  //  (1 to 1000).foreach(simpleActor ! _)

  // complex flows:
  val complexFlow = Flow[Int].map { x =>
    // simulating a long computation
    Thread.sleep(1000)
    x + 1
  }
  val complexFlow2 = Flow[Int].map { x =>
    // simulating a long computation
    Thread.sleep(1000)
    x * 10
  }

  //  simpleSource.via(complexFlow).via(complexFlow2).to(simpleSink).run()

  // async boundary
  // simpleSource.via(simpleFlow).async.via(simpleFlow2).async.to(simpleSink).run()

  // the async method which introduce the async boundaries to the code speeds up the code
  // making it to run on several different actors

  // ordering guarantees
  Source(1 to 3)
    .map(element => { println(s"Flow A: $element"); element}).async
    .map(element => { println(s"Flow B: $element"); element}).async
    .map(element => { println(s"Flow C: $element"); element}).async
    .runWith(Sink.ignore)


}
