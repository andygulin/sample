package examples.showcase.mongodb;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.collections.Lists;

import com.mongodb.Block;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.FindIterable;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.connection.ClusterSettings;

public class MongodbAsyncTest {

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
		collection.insertOne(document, new SingleResultCallback<Void>() {
			@Override
			public void onResult(Void result, Throwable t) {
				System.out.println("inserted");
			}
		});

		document = new Document().append("name", "bbb").append("age", 12);
		collection.insertOne(document, new SingleResultCallback<Void>() {
			@Override
			public void onResult(Void result, Throwable t) {
				System.out.println("inserted");
			}
		});
	}

	@Test
	public void findOne() {
		FindIterable<Document> iter = collection.find(Filters.eq("name", "aaa"));
		iter.first(new SingleResultCallback<Document>() {
			@Override
			public void onResult(Document result, Throwable t) {
				System.out.println(result.toJson());
			}
		});
	}

	@Test
	public void find() {
		collection.find().forEach(new Block<Document>() {
			@Override
			public void apply(Document t) {
				System.out.println(t.toJson());
			}
		}, new SingleResultCallback<Void>() {
			@Override
			public void onResult(Void result, Throwable t) {
				System.out.println("done");
			}
		});
	}

	@After
	public void close() {
		client.close();
	}
}
