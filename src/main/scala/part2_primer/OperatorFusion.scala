package part2_primer

import akka.actor.ActorSystem
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
  simpleSource.via(simpleFlow).via(simpleFlow2).to(simpleSink).run() // a valid Akka Stream runable graph

  // Attention this run on the same Actor a.k.a. operator/component FUSION

  

}
