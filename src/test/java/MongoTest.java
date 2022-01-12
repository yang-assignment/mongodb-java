import org.example.mongodb.MongoUtils;
import com.mongodb.client.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.Date;

public class MongoTest {
    private static MongoDatabase mongoDatabase;

    @Test
    public void getDBs() {
        MongoClient mongoClient = MongoUtils.getMongoClient();
        MongoIterable<String> databaseNames = mongoClient.listDatabaseNames();
        for (String databaseName : databaseNames) {
            System.out.println(databaseName);
        }
    }

    @Test
    public void getCollection() {
        mongoDatabase = MongoUtils.getMongoConn();
        MongoIterable<String> listCollectionNames = mongoDatabase.listCollectionNames();
        for (String collectionName : listCollectionNames) {
            System.out.println(collectionName);
        }
    }

    @Test
    public void createCollection() {
        mongoDatabase = MongoUtils.getMongoConn();
        mongoDatabase.createCollection("test");
    }

    @Test
    public void dropCollection() {
        mongoDatabase = MongoUtils.getMongoConn();
        MongoCollection<Document> test = mongoDatabase.getCollection("test");
        test.drop();
    }

    @Test
    public void findDocument() {
        mongoDatabase = MongoUtils.getMongoConn();
        MongoCollection<Document> comment = mongoDatabase.getCollection("comment");
        FindIterable<Document> documents = comment.find();
        for(Document document : documents) {
            System.out.println(document);
        }
    }
    @Test

    public void insertOneDocument() {
        mongoDatabase = MongoUtils.getMongoConn();
        MongoCollection<Document> comment = mongoDatabase.getCollection("comment");
        Document document = new Document("_id","7").append("articleid", "100001")
                .append("content", "吃饭前，先喝杯水或一碗汤，可减少饭量，对控制体重有帮助")
                .append("userid", "1007").append("nickname", "玛丽莲·梦露").append("age", "18")
                .append("phone", "13937165554").append("createtime", new Date())
                .append("likenum", "8888").append("state", "null");
        comment.insertOne(document);
    }

    @Test
    public void updateDocument() {
        mongoDatabase = MongoUtils.getMongoConn();
        MongoCollection<Document> comment = mongoDatabase.getCollection("comment");
        Document document = new Document("content", "饭后半小时最好不要和大量的水，以免冲淡胃液，稀释胃酸，损害消化功能");
        comment.updateOne(Filters.eq("content", "吃饭前，先喝杯水或一碗汤，可减少饭量，对控制体重有帮助"), new Document("$set",document));
    }

    @Test
    public void deleteDocument() {
        mongoDatabase = MongoUtils.getMongoConn();
        MongoCollection<Document> comment = mongoDatabase.getCollection("comment");
        comment.deleteOne(Filters.eq("_id", "7"));
    }
}
