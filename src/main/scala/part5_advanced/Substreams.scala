package part5_advanced

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

object Substreams extends App {

  implicit val system = ActorSystem("Substreams")
  implicit val materializer = ActorMaterializer()

  // 1 - grouping a stream by a certain function
  val wordSource = Source(List("Akka", "is", "amazing", "learning", "substreams"))
  val groups = wordSource.groupBy(30, word => if(word.isEmpty) '\0' else word.toLowerCase().charAt(0))

  groups.to(Sink.fold(0)((count, word) => {
    val newCount = count + 1
    println(s"I just received $word, count is $newCount")
    newCount
  }))
    .run()

}
