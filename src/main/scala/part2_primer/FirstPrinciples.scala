package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.Future

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
//  graph.run()

  // flow is the akka stream component which job is to transform elements
  val flow = Flow[Int].map(x => x + 1)
  // flow can be attached to sources
  val sourceWithFlow = source.via(flow)
  val flowWithSink = flow.to(sink)

  // a valid graph
//  sourceWithFlow.to(sink).run()
//  source.to(flowWithSink).run()
//  source.via(flow).to(sink).run()

  // nulls are not allow to emit elements
  val illegalSource = Source.single[String](null)
  illegalSource.to(Sink.foreach(println)).run()
  // in order the null limitations to be overcome Options can be used

  // different kind of sources
  val finiteSource = Source.single(1)
  val anotherFiniteSource = Source(List(1, 2, 3))
  val emptySource = Source.empty[Int]
  val infiniteSource = Source(Stream.from(1)) // do not confuse akka stream with a "collection" Stream

  import scala.concurrent.ExecutionContext.Implicits.global
  val futureSource = Source.fromFuture(Future(42))

  // sinks
  val theMostBoringSink = Sink.ignore
  val foreachSink = Sink.foreach[String](println)
  val headSink = Sink.head[Int] // retrieves head then close the stream
  val foldSink = Sink.fold[Int, Int](0)((a,b) => a + b)

  // flows - usually mapped to collection operators
  val mapFlow = Flow[Int].map(x => 2 * x)
  val takeFlow = Flow[Int].take(5)
  // drop, filter
  // NOT have flatMap

  // the way the stream is constructed
  // source -> flow -> flow -> -> ... -> sink
  val doubleFlowGraph = source.via(mapFlow).via(takeFlow).to(sink)
  doubleFlowGraph.run()

  // syntactic sugars
  val mapSource = Source(1 to 10).map(x => x * 2) // This is equivalent to saying Source(1 to 10).via(Flow[Int].map(x => x * 2))



}
