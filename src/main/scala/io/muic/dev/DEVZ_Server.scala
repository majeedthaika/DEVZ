package io.muic.dev

import scala.io.Source

object DEVZ_Server {
  def main(args: Array[String]) {
    for (ln <- Source.stdin.getLines) println(ln)
  }
}
