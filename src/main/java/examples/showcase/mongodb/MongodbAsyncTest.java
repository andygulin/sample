package examples.showcase.mongodb;

import com.mongodb.ServerAddress;
import com.mongodb.async.client.*;
import com.mongodb.connection.ClusterSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.collections.Lists;

import java.net.UnknownHostException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongodbAsyncTest {

	private static final Logger logger = LogManager.getLogger(MongodbAsyncTest.class);

	private MongoClient client = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;
	private String dbName = "test";
	private String collectionName = "user";

	@Before
	public void init() throws UnknownHostException {
		List<ServerAddress> hosts = Lists.newArrayList(new ServerAddress("localhost", 27017));
		ClusterSettings clusterSettings = ClusterSettings.builder().hosts(hosts).build();
		MongoClientSettings settings = MongoClientSettings.builder().clusterSettings(clusterSettings).build();
		client = MongoClients.create(settings);
		db = client.getDatabase(dbName);
		collection = db.getCollection(collectionName);
	}

	@Test
	public void insert() {
		Document document = new Document().append("name", "aaa").append("age", 11);
		collection.insertOne(document, (result, t) -> logger.info("inserted"));

		document = new Document().append("name", "bbb").append("age", 12);
		collection.insertOne(document, (result, t) -> logger.info("inserted"));
	}

	@Test
	public void findOne() {
		FindIterable<Document> iter = collection.find(eq("name", "aaa"));
		iter.first((result, t) -> logger.info(result.toJson()));
	}

	@Test
	public void find() {
		collection.find().forEach(t -> logger.info(t.toJson()), (result, t) -> logger.info("done"));
	}

	@After
	public void close() {
		client.close();
	}
}
