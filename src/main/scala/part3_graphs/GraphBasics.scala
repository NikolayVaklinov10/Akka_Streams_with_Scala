package part3_graphs

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Balance, Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source, Zip}

object GraphBasics extends App {

  // the implicits for th code
  implicit val system = ActorSystem("GraphBasics")
  implicit val materializer = ActorMaterializer()

  val input = Source(1 to 1000)
  val incrementer = Flow[Int].map(x => x + 1) // hard computation
  val multiplier = Flow[Int].map(x => x * 10) // hard computation
  val output = Sink.foreach[(Int, Int)](println)

  // step 1 - setting up the fundamentals for the graph
  val graph = RunnableGraph.fromGraph(
    GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>  // builder = MUTABLE data structure
      import GraphDSL.Implicits._ // brings some nice operators into scope

      // step 2 - add the necessary components of this graph
      val broadcast = builder.add(Broadcast[Int](2)) // fan-out operator
      val zip = builder.add(Zip[Int, Int]) // fan-in operator

      // step 3 - tying up the components
      input ~> broadcast

      broadcast.out(0) ~> incrementer ~> zip.in0
      broadcast.out(1) ~> multiplier ~> zip.in1

      zip.out ~> output

      // step 4 - return a closed shape
      ClosedShape // FREEZE the builder's shape
      // shape
    } // graph
  ) // runnable graph

//  graph.run() // run the graph and materialize it

  val firstSink = Sink.foreach[Int](x => println(s"First sink: $x"))
  val secondSink = Sink.foreach[Int](y => println(s"Second sink: $y"))

  /**
   * exercise 1: feed a source into 2 sinks at the same time (hint: use a broadcast)
   */

  // 1) again the fundamental part of the graph
  val sourceToTwoSinksGraph = RunnableGraph.fromGraph(
    GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      // 2) add the necessary components of the graph
      val broadcast = builder.add(Broadcast[Int](2))

      // 3) tying up the components
      input ~> broadcast ~> firstSink// second version of the code below
      broadcast ~> secondSink

//      broadcast.out(0) ~> firstSink // first option of this part of the code
//      broadcast.out(1) ~> secondSink // first option of this part of the code


      // 4) return ClosedShape
      ClosedShape

    }
  )
  /**
   * exercise 2 balance
   */
  // some sources
  import scala.concurrent.duration._
  val fastSource = input.throttle(5, 1 second)
  val slowSource = input.throttle(2, 1 second)

  // a couple of sinks
  val sink1 = Sink.fold[Int, Int](0) ((count, _) => {
    println(s"Sink 1 number of elements: $count")
    count + 1
  })
  val sink2 = Sink.fold[Int, Int](0) ((count, _) => {
    println(s"Sink 2 number of elements: $count")
    count + 1
  })




  // step 1
  val balanceGraph = RunnableGraph.fromGraph(
    GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      // step 2 the necessary components
      val merge = builder.add(Merge[Int](2))
      val balance = builder.add(Balance[Int](2))

      // step 3 tying up the components
      fastSource ~> merge ~> balance ~> sink1
      slowSource ~> merge; balance ~> sink2

      // step 4 the ClosedShape
      ClosedShape
    }
  )

  balanceGraph.run()
}
