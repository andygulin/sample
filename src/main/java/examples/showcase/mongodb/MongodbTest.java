package examples.showcase.mongodb;

import com.google.common.collect.Lists;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.model.Filters;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MongodbTest {

    private static final Logger logger = LogManager.getLogger(MongodbTest.class);

    private MongoClient client = null;
    private MongoDatabase db = null;
    private MongoCollection<Document> collection = null;
    private String dbName;
    private String collectionName;
    private List<ServerAddress> seeds;

    @Before
    public void init() throws ConfigurationException, IOException {
        Resource resource = new ClassPathResource("mongodb.properties");
        Configuration configuration = new PropertiesConfiguration(resource.getFile());
        dbName = configuration.getString("mongodb.dbName");
        collectionName = configuration.getString("mongodb.collectionName");
        String host = configuration.getString("mongodb.host");
        int port = configuration.getInt("mongodb.port");
        seeds = Lists.newArrayList(new ServerAddress(host, port));
    }

    @Before
    public void conn() throws UnknownHostException {
        MongoClientOptions options = MongoClientOptions.builder().build();
        client = new MongoClient(seeds, options);
        db = client.getDatabase(dbName);
        collection = db.getCollection(collectionName);
    }

    @Test
    public void dbs() {
        Collection<DB> dbs = client.getUsedDatabases();
        for (DB db : dbs) {
            logger.info(db);
        }
    }

    @Test
    public void dropDatabase1() {
        client.dropDatabase(dbName);
    }

    @Test
    public void dropDatabase2() {
        db.drop();
    }

    @Test
    public void collectionNames() {
        MongoIterable<String> iter = db.listCollectionNames();
        iter.forEach((Block<String>) t -> logger.info(t));

        logger.info(StringUtils.repeat("=", 80));

        ListCollectionsIterable<Document> iterable = db.listCollections();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            logger.info(doc.getString("name"));
        }
    }

    @Test
    public void codecRegistry() {
        CodecRegistry registry = db.getCodecRegistry();
        logger.info(registry);
    }

    @Test
    public void insert() {
        Document document = new Document().append("name", "aaa").append("age", new BsonInt32(11))
                .append("money", new BsonInt64(1836238L)).append("createAt", new Date());
        collection.insertOne(document);
        logger.info(document.get("_id"));

        document = new Document().append("name", "bbb").append("age", new BsonInt32(12))
                .append("money", new BsonInt64(7298368L)).append("createAt", new Date());
        collection.insertOne(document);
        logger.info(document.get("_id"));
    }

    @Test
    public void insertImages() throws Exception {
        GridFSBucket gridFSBucket = GridFSBuckets.create(db);
        Collection<File> files = FileUtils.listFiles(new File(("E:\\dl")), new String[]{"jpg", "png"}, false);
        for (File file : files) {
            InputStream is = FileUtils.openInputStream(file);
            ObjectId fileId = gridFSBucket.uploadFromStream(file.getName(), is);
            logger.info(fileId);
        }
    }

    @Test
    public void insertMany() {
        final int COUNT = 50000;
        List<Document> documents = Lists.newArrayListWithCapacity(COUNT);
        for (int i = 0; i < COUNT; i++) {
            Document document = new Document();
            document.put("name", random(5));
            document.put("age", new BsonInt32(randomInt(2)));
            document.put("money", new BsonInt64(randomLong(10)));
            document.put("createAt", new Date());
            documents.add(document);
        }
        collection.insertMany(documents);
    }

    @Test
    public void count() {
        long count = collection.count();
        logger.info(count);
    }

    @Test
    public void findOne() {
        FindIterable<Document> iter = collection.find(Filters.eq("name", "aaa"));
        Document doc = iter.first();
        logger.info(doc.toJson());
    }

    @Test
    public void find() {
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            logger.info(doc.toJson());
        }
        cursor.close();
    }

    @Test
    public void mapReduce() {
        String map = "function() { emit(this.name, {count:1});}";
        String reduce = "function(key, values) {";
        reduce += "var total = 0;";
        reduce += "for(var i=0;i<values.length;i++){total += values[i].count;}";
        reduce += "return {count:total};}";

        MapReduceIterable<Document> iter = collection.mapReduce(map, reduce);
        MongoCursor<Document> cursor = iter.iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            logger.info(doc.toJson());
        }
        cursor.close();
    }

    @After
    public void close() {
        client.close();
    }

    private String random(int count) {
        return RandomStringUtils.random(count, "abcdefghijklmnopqrstuvwxyz");
    }

    private int randomInt(int count) {
        return NumberUtils.toInt(RandomStringUtils.random(count, "123456789"));
    }

    private long randomLong(int count) {
        return NumberUtils.toLong(RandomStringUtils.random(count, "123456789"));
    }
}
