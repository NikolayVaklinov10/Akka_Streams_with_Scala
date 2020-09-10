package part5_advanced

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}

import scala.util.{Failure, Success}


object Substreams extends App {

  implicit val system = ActorSystem("Substreams")
  implicit val materializer = ActorMaterializer()
  import system.dispatcher

  // 1 - grouping a stream by a certain function
  val wordSource = Source(List("Akka", "is", "amazing", "learning", "substreams"))
  val groups = wordSource.groupBy(30, word => if(word.isEmpty) '\0' else word.toLowerCase().charAt(0))

  groups.to(Sink.fold(0)((count, word) => {
    val newCount = count + 1
    println(s"I just received $word, count is $newCount")
    newCount
  }))
    .run()

  // 2 - merge substreams back
  val textSource = Source(List(
    "The price of Tesla shares are too high",
    "at the moment the markets are overvalued",
    "FED and the other central banks are responsible"
  ))

  val totalCharCountFuture = textSource
    .groupBy(2, string => string.length % 2)
    .map(_.length) // do the expensive computation here
    .mergeSubstreamsWithParallelism(2)
    .toMat(Sink.reduce[Int](_ + _))(Keep.right)
    .run()

  totalCharCountFuture.onComplete {
    case Success(value) => println(s"Total char count: $value")
    case Failure(ex) => println(s"Char computation failed: $ex")
  }

  // 3 - splitting a stream into substreams, when a condition is met

  val text =
    "I love Akka Streams\n" +
      "this is amazing\n" +
      "learning from Rock the JVM\n"

  val anotherCharCountFuture = Source(text.toList)
    .splitWhen(c => c == '\n')
    .filter(_ != '\n')
    .map(_ => 1)
    .mergeSubstreams
    .toMat(Sink.reduce[Int](_ + _))(Keep.right)
    .run()

  anotherCharCountFuture.onComplete {
    case Success(value) => println(s"Total char count alternative: $value")
    case Failure(ex) => println(s"Char computation failed: $ex")
  }





}
