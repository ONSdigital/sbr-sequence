import java.net.ConnectException

import org.scalatest._

/**
  * You will need a Zookeeper instance running on localhost:2181 for these tests
  */
class TestSequenceGenerator extends FunSuite with Matchers {

  test("Generate next Sequence Number") {
    val hostName = "localhost:2181" // This needs to be up and running
    val service: SequenceGenerator = SequenceGenerator.fromHost(hostName = hostName,  sessionTimeoutSec = 1, connectionTimeoutSec = 1)
    val lastSequence: Long = service.currentSequence.toLong + 1
    val nextSequence: Long = service.nextSequence.toLong
    assert(lastSequence === nextSequence)
    Console.out.println(s"Next Sequence Number: $nextSequence")
  }

  test("Performance figures") {
    val service: SequenceGenerator = SequenceGenerator.fromHost("localhost:2181")
    testSpeed(service, 15000)
  }

  def testSpeed(service: SequenceGenerator, loop: Int = 10000): Unit = {
    val res = timeCall {for (i <- 1 to loop) service.nextSequence}
    val seconds = res / 1000000000.0
    val cps = loop / seconds
    Console.out.println(f"Performance Results: Number of calls $loop, Elapsed Time: $seconds%2.2f sec, $cps%2.2f calls/sec")
  }

  def timeCall[R](block: => R): Double = {
    val start = System.nanoTime
    block
    System.nanoTime - start
  }
}

