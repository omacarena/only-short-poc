package my.java.spring.mongo.myapp.repositories;

import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MyMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MyMongoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public boolean canConnect() {
        try {
            mongoTemplate.getDb().getStats();
        }
        catch (MongoException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
