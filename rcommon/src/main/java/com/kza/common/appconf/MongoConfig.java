package com.kza.common.appconf;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;

/**
 * Created by kza on 2015/9/24.
 */
@Configuration
public class MongoConfig {

    @Autowired
    private Environment env;

    private MongoClient mongoClient() throws UnknownHostException {

        return new MongoClient(new ServerAddress(env.getProperty("mongo.host"), Integer.parseInt(env.getProperty("mongo.port"))), mongoClientOptions());
    }

    private MongoClientOptions mongoClientOptions() {
        return MongoClientOptions
                .builder()
                .socketKeepAlive(true)
                .socketTimeout(1500)
                .maxWaitTime(1500)
                .connectTimeout(1000)
                .connectionsPerHost(8)
                .threadsAllowedToBlockForConnectionMultiplier(4)
                .readPreference(ReadPreference.secondaryPreferred())
                .build();
    }

    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient(), env.getProperty("mongo.databaseName"));
        return simpleMongoDbFactory;
    }

    private MongoConverter mongoConverter() throws UnknownHostException {
        DefaultMongoTypeMapper defaultMongoTypeMapper = new DefaultMongoTypeMapper(null);
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()), new MongoMappingContext());
        mappingMongoConverter.setTypeMapper(defaultMongoTypeMapper);
        return mappingMongoConverter;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        MongoTemplate template = new MongoTemplate(mongoDbFactory(), mongoConverter());
        template.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return template;
    }
}
