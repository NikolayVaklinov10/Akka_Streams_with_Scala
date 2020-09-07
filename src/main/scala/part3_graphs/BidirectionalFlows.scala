package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object BidirectionalFlows extends App {

  implicit val system = ActorSystem("BidirectionalFlows")
  implicit val materializer = ActorMaterializer()

  /*
    Example: cryptography
   */
  def encrypt(n: Int)(string: String) = string.map(c => (c + n).toChar)
  def decrypt(n: Int)(string: String) = string.map(c => (c - n).toChar)

  println(encrypt(3)("Akka"))
  println(decrypt(3)("Dnnd"))

}
