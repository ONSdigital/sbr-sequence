import java.nio.ByteBuffer
import java.util
import java.util.Collections

import org.apache.zookeeper.ZooDefs.Perms
import org.apache.zookeeper.data.{ACL, Id, Stat}
import org.apache.zookeeper.{CreateMode, KeeperException, Transaction, ZooKeeper}

object AtomicSequence {

  val ANYONE_ID = new Id("world", "anyone")
  val OPEN_ACL = new util.ArrayList[ACL](Collections.singletonList(new ACL(Perms.ALL, ANYONE_ID)))

  def getNextSequence(state: String): Long = {

    val s: Stat = new Stat
    val server: String = "127.0.0.1:2181"
    var committed: Boolean = false
    var id: Long = 0
    val zk: ZooKeeper = new ZooKeeper(server, 3000, null)

    zk.create("/transaction", "transaction".getBytes(), OPEN_ACL, CreateMode.PERSISTENT)

    while (!committed) {

      val transaction : Transaction  = zk.transaction

      transaction.create("/transaction/good", new Array[Byte](0), OPEN_ACL, CreateMode.PERSISTENT)

      val buf = ByteBuffer.wrap(zk.getData(state, false, s))
      id = buf.array().map(_.toChar).mkString.toInt + 1

      buf.rewind
      buf.put(id.toString.getBytes)

      try {
        transaction.setData(state, buf.array(), s.getVersion)
        committed = true
      } catch {
        case ke: KeeperException.BadVersionException => println("Got a keeper exception")
        case e: InterruptedException => println("Got an interrupted exception")
      } finally {
        zk.close()
      }
    }
    id
  }

}
