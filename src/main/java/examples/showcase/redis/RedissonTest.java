package examples.showcase.redis;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.MessageListener;
import org.redisson.core.RAtomicLong;
import org.redisson.core.RBucket;
import org.redisson.core.RCountDownLatch;
import org.redisson.core.RDeque;
import org.redisson.core.RHyperLogLog;
import org.redisson.core.RList;
import org.redisson.core.RLock;
import org.redisson.core.RMap;
import org.redisson.core.RQueue;
import org.redisson.core.RSet;
import org.redisson.core.RSortedSet;
import org.redisson.core.RTopic;

public class RedissonTest {

	private static final Logger logger = LogManager.getLogger(RedissonTest.class);

	private RedissonClient redisson;

	@Before
	public void init() {
		Config config = new Config();
		config.useSingleServer().setAddress("192.168.68.130:6379");
		redisson = Redisson.create(config);
	}

	@Test
	public void RAtomicLong() {
		RAtomicLong atomicLong = redisson.getAtomicLong("AtomicLong");
		atomicLong.set(1L);
		atomicLong.incrementAndGet();

		print(atomicLong.get());
	}

	@Test
	public void Bucket() throws InterruptedException, ExecutionException {
		RBucket<String> bucket = redisson.getBucket("Bucket");

		bucket.set("aaa");
		print(bucket.get());

		bucket.setAsync("bbb");
		print(bucket.getAsync().get());
	}

	@Test
	public void CountDownLatch_await() throws InterruptedException {
		RCountDownLatch countDownLatch = redisson.getCountDownLatch("CountDownLatch");
		countDownLatch.trySetCount(2L);
		countDownLatch.await();
	}

	@Test
	public void CountDownLatch_countDown() {
		RCountDownLatch countDownLatch = redisson.getCountDownLatch("CountDownLatch");
		countDownLatch.countDown();
	}

	@Test
	public void Deque() {
		RDeque<String> deque = redisson.getDeque("Deque");
		deque.add("aaa");
		deque.add("bbb");
		deque.add("ccc");
		deque.addFirst("zzz");

		deque.pollLast();
		print(deque);
	}

	@Test
	public void Queue() {
		RQueue<String> queue = redisson.getQueue("Queue");
		queue.add("aaa");
		queue.add("bbb");
		queue.add("ccc");

		queue.poll();
		print(queue);
	}

	@Test
	public void List() {
		RList<String> list = redisson.getList("List");
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		list.remove("bbb");
		print(list);
	}

	@Test
	public void Set() {
		RSet<String> set = redisson.getSet("Set");
		set.add("aaa");
		set.addAsync("bbb");
		set.add("ccc");

		set.removeAsync("bbb");
		print(set);
	}

	@Test
	public void Lock() throws InterruptedException {
		RLock lock = redisson.getLock("Lock");
		lock.lock();

		print("lock 3s...");
		TimeUnit.SECONDS.sleep(3L);

		lock.unlock();

		print("unlock...");
	}

	@Test
	public void Topic() {
		RTopic<String> topic = redisson.getTopic("Topic");
		topic.addListener(new MessageListener<String>() {
			@Override
			public void onMessage(String channel, String msg) {
				print(msg);
			}
		});
		topic.publish("hello");
	}

	@Test
	public void HyperLogLog() throws InterruptedException, ExecutionException {
		RHyperLogLog<String> log = redisson.getHyperLogLog("HyperLogLog");
		log.add("aaa");
		log.addAsync("bbb");

		print(log.count());
		print(log.countAsync().get());
	}

	@Test
	public void SortedSet() {
		RSortedSet<String> sortedSet = redisson.getSortedSet("SortedSet");
		sortedSet.add("aaa");
		sortedSet.add("bbb");
		sortedSet.add("111");
		sortedSet.add("213");
		sortedSet.add("a1b");

		print(sortedSet);
	}

	@Test
	public void Map() {
		RMap<String, Object> map = redisson.getMap("Map");
		map.put("name", "aaa");
		map.putAsync("age", 11);
		map.fastPut("address", "shanghai");
		map.fastPutAsync("createAt", new Date());

		print(map);
	}

	@After
	public void shutdown() {
		redisson.shutdown();
	}

	private void print(Object obj) {
		if (obj instanceof Number) {
			logger.info(obj);
		}
		if (obj instanceof String) {
			logger.info(obj);
		}
		if (obj instanceof Collection<?>) {
			Iterator<?> iter = ((Collection<?>) obj).iterator();
			while (iter.hasNext()) {
				logger.info(iter.next());
			}
		}
		if (obj instanceof Map<?, ?>) {
			for (Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
				logger.info(entry.getKey() + " : " + entry.getValue());
			}
		}
	}
}
