import java.lang

import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.recipes.atomic.{AtomicValue, DistributedAtomicLong}
import org.apache.curator.retry.RetryOneTime

class UniqueIDService(hostName: String = "localhost:2181") {

  private val client = CuratorFrameworkFactory.newClient(hostName, new RetryOneTime(1))
  private val dal: DistributedAtomicLong = new DistributedAtomicLong(client, "/ids/enterprise/id", new RetryOneTime(1))

  println()
  println("Starting UniqueIDService: " + hostName)
  println()
  client.start()
}

object UniqueIDService {

  var service: UniqueIDService = new UniqueIDService

  def apply(hostName: String) : UniqueIDService = {
    new UniqueIDService(hostName)
  }

  def nextSequence: String = nextSequence(1)._1

  def nextSequence(batchSize: Long): (String, String) = {
    val id: AtomicValue[lang.Long] = service.dal.add(batchSize)

    "11%07d".format(id.preValue + 1) -> "11%07d".format(id.postValue.longValue)
  }
}
