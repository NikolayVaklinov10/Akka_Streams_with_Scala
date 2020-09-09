package part4_techniques

import java.util.Date

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

import scala.concurrent.Future

object IntegratingWithExternalServices extends App {

  implicit val system = ActorSystem("IntegratingWithExternalServices")
  implicit val materializer = ActorMaterializer()
  import system.dispatcher


  // external service may look like this
  def genericExtService[A, B](element: A): Future[B] = ???


  // a realistic example
  // Simplified PagerDuty
  case class PagerEvent(application: String, description: String, date: Date)

  val eventSource = Source(List(
    PagerEvent("AkkaInfra", "Infrastructure broke", new Date),
    PagerEvent("FastDataPipeline", "Illegal elements in the data pipeline", new Date),
    PagerEvent("AkkaInfra", "A service stopped responding", new Date),
    PagerEvent("SuperFrontend", "A button doesn't", new Date)
  ))

  object PagerService {
    private val engineers = List("Daniel", "Lady Gaga")
    private val emails = Map(
      "Daniel" -> "daniel@rockthejvm.com",
      "John" -> "john@rockthejvm.com",
      "Lady Gaga" -> "ladygaga@rockthejvm.com"
    )

    def processEvent(pagerEvent: PagerEvent) = Future {
      val engineerIndex = ((pagerEvent.date.toInstant.getEpochSecond / (24 * 3600))) % engineers.length
      val engineer = engineers(engineerIndex.toInt)
      val engineerEmail = emails(engineer)

      // page the engineer
      println(s"Sending engineer $engineerEmail a high priority notification: $pagerEvent")
      Thread.sleep(1000)

      // return the email that was paged
      engineerEmail
    }
  }
  val infraEvents = eventSource.filter(_.application == "AkkaInfra")
  val pagedEngineerEmails = infraEvents.mapAsync(parallelism = 4)(event => PagerService.processEvent(event))
  val pagedEmailsSink = Sink.foreach[String](email => println(s"Successfully sent notification to $email"))
  
  pagedEngineerEmails.to(pagedEmailsSink).run()





}
