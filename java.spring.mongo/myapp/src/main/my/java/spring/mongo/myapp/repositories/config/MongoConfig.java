package my.java.spring.mongo.myapp.repositories.config;

import com.mongodb.MongoClientOptions;
import my.java.spring.mongo.myapp.repositories.MyMongoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = MyMongoRepository.class)
public class MongoConfig {

    /**
     * Override client options in order to make the timeout shorter.
     * More on timeouts here: https://scalegrid.io/blog/understanding-mongodb-client-timeout-options/
     */
    @Bean
    public MongoClientOptions mongoClientOptions() {
        return MongoClientOptions.builder()
                // timeout for selecting a server to execute operation on
                .serverSelectionTimeout(3000)
                // after server selection, tries to connect to server
                .socketTimeout(3000)
                .build();
    }
}