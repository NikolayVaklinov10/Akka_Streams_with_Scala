package part3_graphs

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Flow, GraphDSL, Merge, RunnableGraph, Source}

object GraphCycles extends App {

  implicit val system = ActorSystem("GraphCycles")
  implicit val materializer = ActorMaterializer()

  // the graph cycle
  val accelerator = GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._

    val sourceShape = builder.add(Source(1 to 100))
    val mergeShape = builder.add(Merge[Int](2))
    val incrementerShape = builder.add(Flow[Int].map { x =>
      println(s"Accelerating $x")
      x + 1
    })

    sourceShape ~> mergeShape ~> incrementerShape
                   mergeShape <~ incrementerShape

    ClosedShape
  }
  RunnableGraph.fromGraph(accelerator).run()
  // the code above causing graph cycle deadlock!
}
