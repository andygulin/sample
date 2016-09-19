package examples.showcase.zookeeper;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.DataUpdater;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import examples.showcase.User;

public class ZkClientTest extends ZooKeeperClientBaseTest {

	private static final Logger logger = LogManager.getLogger(ZkClientTest.class);

	private ZkClient client;
	private static final String PATH = "/user";

	@Before
	public void init() throws IOException {
		IZkConnection connection = new ZkConnection(SERVERS);
		client = new ZkClient(connection, 3000);
		client.subscribeChildChanges(PATH, new ZkChildListener());
		client.subscribeDataChanges(PATH, new ZkDataListener());
		client.subscribeStateChanges(new ZkStateListener());
	}

	@Test
	public void create() {
		boolean exist = client.exists(PATH);
		if (exist) {
			client.delete(PATH);
		}
		User user = new User(1, "aaa", 11, "shanghai", new Date());
		client.createPersistent(PATH, user);
	}

	@Test
	public void read() {
		User user = client.readData(PATH);
		logger.info(user);
	}

	@Test
	public void update() {
		client.updateDataSerialized(PATH, new DataUpdater<User>() {
			@Override
			public User update(User currentData) {
				User user = currentData;
				user.setAddress("beijing");
				return user;
			}
		});
	}

	@Test
	public void delete() {
		client.delete(PATH);
	}

	@After
	public void close() {
		client.unsubscribeAll();
		client.close();
	}

	private static class ZkChildListener implements IZkChildListener {

		@Override
		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
			logger.info("IZkChildListener -> handleChildChange");
			logger.info(parentPath);
			logger.info(currentChilds);
		}
	}

	private static class ZkDataListener implements IZkDataListener {

		@Override
		public void handleDataDeleted(String dataPath) throws Exception {
			logger.info("IZkDataListener -> handleDataDeleted");
			logger.info(dataPath);
		}

		@Override
		public void handleDataChange(String dataPath, Object data) throws Exception {
			logger.info("IZkDataListener -> handleDataChange");
			logger.info(dataPath);
			logger.info(data);
		}
	}

	private static class ZkStateListener implements IZkStateListener {

		@Override
		public void handleStateChanged(KeeperState state) throws Exception {
			logger.info("IZkStateListener -> handleStateChanged");
			logger.info(state);
		}

		@Override
		public void handleNewSession() throws Exception {
			logger.info("IZkStateListener -> handleNewSession");
		}

		@Override
		public void handleSessionEstablishmentError(Throwable error) throws Exception {
			logger.info("IZkStateListener -> handleSessionEstablishmentError");
			logger.info(error);
		}
	}
}