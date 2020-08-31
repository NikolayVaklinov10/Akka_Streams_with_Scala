package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object FirstPrinciples extends App {

  implicit val system = ActorSystem("FirstPrinciples")
  implicit val materializer = ActorMaterializer()

  // every akka stream starts with a source
  // the source
  val source = Source(1 to 10)
  // every akka stream ends with a sink
  // the sink
  val sink = Sink.foreach[Int](println)

  // in order to create an akka stream the source and the sink have to be connected!!!
  // the so called graph
  // the graph
  val graph = source.to(sink)

  // to start the stream the method run() has to called on the graph
  graph.run()

  // flow is the akka stream component which job is to transform elements
  val flow = Flow[Int].map(x => x + 1)
  // flow can be attached to sources
  val sourceWithFlow = source.via(flow)
  val flowWithSink = flow.to(sink)

  // a valid graph
  sourceWithFlow.to(sink).run()
}
