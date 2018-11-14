
object TestNumber {
  def main(args: Array[String]): Unit = {

    for (i <- 1 to 10) {
      val (start, end) = UniqueIDService.nextSequence(10)
      val num = UniqueIDService.nextSequence

      printf(s"Seq: from $start, to: $end, Number: $num\n")
    }
  }
}
