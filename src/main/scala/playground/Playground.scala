package playground

import akka.actor.{Actor, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

import scala.annotation.tailrec

object Playground extends App {

  implicit val actorSystem = ActorSystem("Playground")
  implicit val materializer = ActorMaterializer()

  Source.single("hello, Streams!").to(Sink.foreach(println)).run()


  case class Incrementer(value: Int)
  case class Decrementer(value: Int)


  class Counter extends Actor {

    var myVariable = 0

    override def receive: Receive = {
      case Incrementer(value) => myVariable += value
      case Decrementer(value) => myVariable -= value
      case "Print" => println(s"I have this fund $myVariable")
    }
  }

  val system = ActorSystem("FirstExercise")
  val actor = system.actorOf(Props[Counter], "myCounter")

  actor ! Incrementer(10)
  actor ! Decrementer(8)
  actor ! "Print"

//  def factorial(n:Int):Int = {
//    def factorialTailrec(remaining:Int, accumulator: Int):Int = {
//      if(remaining.isEmpty) accumulator
//      else (remaining.tail, remaining.head * accumulator)
//    }
//    factorialTailrec(n,0)
//  }








}
