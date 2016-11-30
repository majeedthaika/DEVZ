import java.util.Date

import org.scalatra._

//package io.muic.dev

object DEVZ_Server {
  // Simple server
  import java.io._
  import java.net._
  import scala.io._

  def main(args: Array[String]) {
    val server = new ServerSocket(9999)
    while (true) {
      val s = server.accept()
      val in = new BufferedSource(s.getInputStream()).getLines()
      val out = new PrintStream(System.out)

      while (true) {
        out.println(in.next())
//        out.flush()
      }
//      s.close()
    }
  }
}

class ScalatraExample extends ScalatraServlet {
  get("/") {
    <h1>Hello, world!</h1>
  }
}