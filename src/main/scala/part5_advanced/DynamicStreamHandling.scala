package part5_advanced

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.stream.{ActorMaterializer, KillSwitches}

import scala.concurrent.duration._

object DynamicStreamHandling extends App {

  implicit val system = ActorSystem("DynamicStreamHandling")
  implicit val materializer = ActorMaterializer()
  import system.dispatcher

  // #1: Kill Switch
  val killSwitchFlow = KillSwitches.single[Int]
  val counter = Source(Stream.from(1)).throttle(1, 1 second).log("counter")
  val sink = Sink.ignore

  val killSwitch = counter
    .viaMat(killSwitchFlow)(Keep.right)
    .to(sink)
    .run()

  system.scheduler.scheduleOnce(3 seconds){
    killSwitch.shutdown()
  }





}
