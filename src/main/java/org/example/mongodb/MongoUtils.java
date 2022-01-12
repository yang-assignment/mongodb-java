package org.example.mongodb;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoUtils {
    private static Properties properties;
    private static MongoDatabase mongoDatabase;
    private static InputStream stream = null;
    private static String host;
    private static int port;
    private static String dbname;
    // 1. 创建一个静态代码块，用于初始化工具类中的静态变量，该静态代码块在类加载过程中的初始化阶段执行，并且只执行一次
    static {
        // 判断 properties 集合对象是否为空，为空则创建一个集合对象
        if(properties == null) {
            properties = new Properties();
        }
        /*
        由于我们调用 load 方法，而 load 方法底层抛出了一个 IOException 异常，此异常为编译时期异常
        所以，我们调用 load 方法时，需要处理底层抛过来的异常
         */
        try {
            // 创建一个 InputStream 字节输入流对象，用于接受 mongodb.properties 配置文件中的配置参数
            stream = MongoUtils.class.getClassLoader().getResourceAsStream("mongodb.properties");
            // properties 集合对象调用 load() 方法，将配置参数加载到 properties 集合中
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 根据 mongodb.properties 配置文件中的 key， 获取 value 值
        host = properties.getProperty("host");
        port = Integer.parseInt(properties.getProperty("port"));
        dbname = properties.getProperty("dbname");
    }

    // 2. 定义一个 getMongoClient() 方法，用于获取 MongoDB 数据库的连接对象
    public static MongoClient getMongoClient() {
        // 由于 MongoClient 对象调用 create() 方法，该方法的参数式一个字符串，因此这里将 host 和 port 拼接成字符串，再作为参数传入到该方法中
        String adr = "mongodb://" + host + ":" + port;
        return MongoClients.create(adr);
    }

    // 3. 定义一个 getMongoConn() 方法，用于实现连接指定的 MongoDb 数据库
    public static MongoDatabase getMongoConn() {
        MongoClient mongoClient = getMongoClient();
        mongoDatabase = mongoClient.getDatabase(dbname);
        return mongoDatabase;
    }
}
