
object TestNumber {
  def main(args: Array[String]): Unit = {

    val num = AtomicSequence.getNextSequence("/paul")
    printf(s"Number: $num" )
  }
}
