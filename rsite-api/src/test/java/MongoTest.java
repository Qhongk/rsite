import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaza on 2015/7/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mongo.xml")
public class MongoTest {

    @Document
    public class Person {

        private String id;
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
        }

    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void queryUser() {
        List<Person> users = mongoTemplate.findAll(Person.class, "User");
        for (Person user : users) {
            System.out.println(user + "***************************************");
        }
    }

    @Test
    public void saveUser() {
        Person user = new Person("test2", 22);
        mongoTemplate.save(user, "User");
    }

    @Test
    public void testMongoDriver() throws UnknownHostException {
        List<ServerAddress> sa = new ArrayList<ServerAddress>() {
            {
                this.add(new ServerAddress("localhost", 27017));
            }
        };
        List<MongoCredential> mc = new ArrayList<MongoCredential>();
        mc.add(MongoCredential.createCredential("dbName", "dbName", "password".toCharArray()));
        MongoClient mongoClient = new MongoClient(sa, mc);

        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, "dbName");

        MongoOperations mongoOperations = new MongoTemplate(mongoDbFactory);

        List<Person> users = mongoOperations.findAll(Person.class, "User");
        for (Person user : users) {
            System.out.println(user + "***************************************");
        }
    }
}
