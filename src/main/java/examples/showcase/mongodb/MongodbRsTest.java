package examples.showcase.mongodb;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongodbRsTest {

	private MongoClient client = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;

	@Before
	public void rsInit() throws UnknownHostException {
		List<ServerAddress> seeds = Lists.newArrayList();
		seeds.add(new ServerAddress("192.168.16.128", 27017));
		seeds.add(new ServerAddress("192.168.16.128", 27018));
		MongoClientOptions options = MongoClientOptions.builder().requiredReplicaSetName("rs").build();
		client = new MongoClient(seeds, options);
		db = client.getDatabase("test");
		db.getCollection("user");
		collection = db.getCollection("user");
	}

	@Test
	public void insert() {
		Document document = new Document().append("name", "aaa").append("age", 11);
		collection.insertOne(document);
		System.out.println(document.get("_id"));

		document = new Document().append("name", "bbb").append("age", 12);
		collection.insertOne(document);
		System.out.println(document.get("_id"));
	}

	@After
	public void close() {
		client.close();
	}
}
