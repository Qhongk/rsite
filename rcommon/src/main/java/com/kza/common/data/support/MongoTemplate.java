package com.kza.common.data.support;

import com.mongodb.*;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by rick.kong.
 */
public class MongoTemplate {

    private MongoOperations mongoOperations;

    public MongoTemplate(final String host, final int port, final String username, final String password, final String databaseName) throws UnknownHostException {

        List<ServerAddress> sa = new ArrayList<>();
        sa.add(new ServerAddress(host, port));
        List<MongoCredential> mc = new ArrayList<>();
        mc.add(com.mongodb.MongoCredential.createCredential(username, databaseName, password.toCharArray()));
        MongoClient mongoClient = new MongoClient(sa, mc);
        mongoOperations = new org.springframework.data.mongodb.core.MongoTemplate(new SimpleMongoDbFactory(mongoClient, databaseName));
    }

    public MongoTemplate(final String host, final int port, final String username, final String password, final String databaseName, final MongoClientOptions mongoClientOptions) throws UnknownHostException {

        List<ServerAddress> sa = new ArrayList<>();
        sa.add(new ServerAddress(host, port));
        List<MongoCredential> mc = new ArrayList<>();
        mc.add(com.mongodb.MongoCredential.createCredential(username, databaseName, password.toCharArray()));
        MongoClient mongoClient = new MongoClient(sa, mc, mongoClientOptions);
        mongoOperations = new org.springframework.data.mongodb.core.MongoTemplate(new SimpleMongoDbFactory(mongoClient, databaseName));
    }

    public MongoTemplate(final String host, final int port, final String username, final String password, final String databaseName, DefaultMongoTypeMapper mongoTypeMapper) throws UnknownHostException {

        List<ServerAddress> sa = new ArrayList<>();
        sa.add(new ServerAddress(host, port));
        List<MongoCredential> mc = new ArrayList<>();
        mc.add(com.mongodb.MongoCredential.createCredential(username, databaseName, password.toCharArray()));
        MongoClient mongoClient = new MongoClient(sa, mc);
        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, databaseName);
        
    	MappingMongoConverter converter =
    		new MappingMongoConverter(mongoDbFactory, new MongoMappingContext());
    	converter.setTypeMapper(mongoTypeMapper);
        
        mongoOperations = new org.springframework.data.mongodb.core.MongoTemplate(new SimpleMongoDbFactory(mongoClient, databaseName), converter);
    }

    public String getCollectionName(Class<?> aClass) {
        return mongoOperations.getCollectionName(aClass);
    }

    public DBCollection getCollection(String s) {
        return mongoOperations.getCollection(s);
    }

    public boolean collectionExists(String s) {
        return mongoOperations.collectionExists(s);
    }

    public <T> List<T> findAll(Class<T> aClass) {
        return mongoOperations.findAll(aClass);
    }

    public <T> MapReduceResults<T> mapReduce(String s, String s1, String s2, MapReduceOptions mapReduceOptions, Class<T> aClass) {
        return mongoOperations.mapReduce(s, s1, s2, mapReduceOptions, aClass);
    }

    public void remove(Object o) {
        mongoOperations.remove(o);
    }

    public <T> List<T> findAll(Class<T> aClass, String s) {
        return mongoOperations.findAll(aClass, s);
    }

    public <T> T findAndModify(Query query, Update update, Class<T> aClass, String s) {
        return mongoOperations.findAndModify(query, update, aClass, s);
    }

    public <T> T findAndModify(Query query, Update update, FindAndModifyOptions findAndModifyOptions, Class<T> aClass) {
        return mongoOperations.findAndModify(query, update, findAndModifyOptions, aClass);
    }

    public <T> GroupByResults<T> group(Criteria criteria, String s, GroupBy groupBy, Class<T> aClass) {
        return mongoOperations.group(criteria, s, groupBy, aClass);
    }

    public WriteResult upsert(Query query, Update update, Class<?> aClass) {
        return mongoOperations.upsert(query, update, aClass);
    }

    public WriteResult upsert(Query query, Update update, Class<?> aClass, String s) {
        return mongoOperations.upsert(query, update, aClass, s);
    }

    public CommandResult executeCommand(DBObject dbObject) {
        return mongoOperations.executeCommand(dbObject);
    }

    public IndexOperations indexOps(Class<?> aClass) {
        return mongoOperations.indexOps(aClass);
    }

    public <T> List<T> find(Query query, Class<T> aClass, String s) {
        return mongoOperations.find(query, aClass, s);
    }

    public void insert(Object o) {
        mongoOperations.insert(o);
    }

    public void insert(Collection<? extends Object> collection, Class<?> aClass) {
        mongoOperations.insert(collection, aClass);
    }

    public void executeQuery(Query query, String s, DocumentCallbackHandler documentCallbackHandler) {
        mongoOperations.executeQuery(query, s, documentCallbackHandler);
    }

    public boolean exists(Query query, Class<?> aClass, String s) {
        return mongoOperations.exists(query, aClass, s);
    }

    public CommandResult executeCommand(String s) {
        return mongoOperations.executeCommand(s);
    }

    public void remove(Query query, Class<?> aClass, String s) {
        mongoOperations.remove(query, aClass, s);
    }

    public <T> GeoResults<T> geoNear(NearQuery nearQuery, Class<T> aClass) {
        return mongoOperations.geoNear(nearQuery, aClass);
    }

    public CommandResult executeCommand(DBObject dbObject, int i) {
        return mongoOperations.executeCommand(dbObject, i);
    }

    public Set<String> getCollectionNames() {
        return mongoOperations.getCollectionNames();
    }

    public void remove(Query query, String s) {
        mongoOperations.remove(query, s);
    }

    public <T> T execute(DbCallback<T> dbCallback) {
        return mongoOperations.execute(dbCallback);
    }

    public <T> T findAndRemove(Query query, Class<T> aClass) {
        return mongoOperations.findAndRemove(query, aClass);
    }

    public long count(Query query, Class<?> aClass) {
        return mongoOperations.count(query, aClass);
    }

    public WriteResult updateFirst(Query query, Update update, Class<?> aClass, String s) {
        return mongoOperations.updateFirst(query, update, aClass, s);
    }

    public WriteResult updateMulti(Query query, Update update, Class<?> aClass, String s) {
        return mongoOperations.updateMulti(query, update, aClass, s);
    }

    public <T> void dropCollection(Class<T> aClass) {
        mongoOperations.dropCollection(aClass);
    }

    public DBCollection createCollection(String s) {
        return mongoOperations.createCollection(s);
    }

    public <T> T execute(Class<?> aClass, CollectionCallback<T> collectionCallback) {
        return mongoOperations.execute(aClass, collectionCallback);
    }

    public WriteResult updateFirst(Query query, Update update, String s) {
        return mongoOperations.updateFirst(query, update, s);
    }

    public <T> T execute(String s, CollectionCallback<T> collectionCallback) {
        return mongoOperations.execute(s, collectionCallback);
    }

    public <O> AggregationResults<O> aggregate(Aggregation aggregation, Class<?> aClass, Class<O> aClass1) {
        return mongoOperations.aggregate(aggregation, aClass, aClass1);
    }

    public boolean exists(Query query, String s) {
        return mongoOperations.exists(query, s);
    }

    public MongoConverter getConverter() {
        return mongoOperations.getConverter();
    }

    public boolean exists(Query query, Class<?> aClass) {
        return mongoOperations.exists(query, aClass);
    }

    public DBCollection createCollection(String s, CollectionOptions collectionOptions) {
        return mongoOperations.createCollection(s, collectionOptions);
    }

    public <T> MapReduceResults<T> mapReduce(Query query, String s, String s1, String s2, MapReduceOptions mapReduceOptions, Class<T> aClass) {
        return mongoOperations.mapReduce(query, s, s1, s2, mapReduceOptions, aClass);
    }

    public <O> AggregationResults<O> aggregate(TypedAggregation<?> typedAggregation, Class<O> aClass) {
        return mongoOperations.aggregate(typedAggregation, aClass);
    }

    public void remove(Object o, String s) {
        mongoOperations.remove(o, s);
    }

    public <T> boolean collectionExists(Class<T> aClass) {
        return mongoOperations.collectionExists(aClass);
    }

    public <T> MapReduceResults<T> mapReduce(Query query, String s, String s1, String s2, Class<T> aClass) {
        return mongoOperations.mapReduce(query, s, s1, s2, aClass);
    }

    public void save(Object o, String s) {
        mongoOperations.save(o, s);
    }

    public WriteResult upsert(Query query, Update update, String s) {
        return mongoOperations.upsert(query, update, s);
    }

    public void insert(Collection<? extends Object> collection, String s) {
        mongoOperations.insert(collection, s);
    }

    public <O> AggregationResults<O> aggregate(Aggregation aggregation, String s, Class<O> aClass) {
        return mongoOperations.aggregate(aggregation, s, aClass);
    }

    public void insert(Object o, String s) {
        mongoOperations.insert(o, s);
    }

    public <T> T executeInSession(DbCallback<T> dbCallback) {
        return mongoOperations.executeInSession(dbCallback);
    }

    public void dropCollection(String s) {
        mongoOperations.dropCollection(s);
    }

    public <T> DBCollection createCollection(Class<T> aClass, CollectionOptions collectionOptions) {
        return mongoOperations.createCollection(aClass, collectionOptions);
    }

    public long count(Query query, String s) {
        return mongoOperations.count(query, s);
    }

    public WriteResult updateMulti(Query query, Update update, String s) {
        return mongoOperations.updateMulti(query, update, s);
    }

    public <T> List<T> find(Query query, Class<T> aClass) {
        return mongoOperations.find(query, aClass);
    }

    public <T> T findById(Object o, Class<T> aClass) {
        return mongoOperations.findById(o, aClass);
    }

    public void insertAll(Collection<? extends Object> collection) {
        mongoOperations.insertAll(collection);
    }

    public void remove(Query query, Class<?> aClass) {
        mongoOperations.remove(query, aClass);
    }

    public <T> MapReduceResults<T> mapReduce(String s, String s1, String s2, Class<T> aClass) {
        return mongoOperations.mapReduce(s, s1, s2, aClass);
    }

    public void save(Object o) {
        mongoOperations.save(o);
    }

    public <T> GroupByResults<T> group(String s, GroupBy groupBy, Class<T> aClass) {
        return mongoOperations.group(s, groupBy, aClass);
    }

    public WriteResult updateMulti(Query query, Update update, Class<?> aClass) {
        return mongoOperations.updateMulti(query, update, aClass);
    }

    public <O> AggregationResults<O> aggregate(TypedAggregation<?> typedAggregation, String s, Class<O> aClass) {
        return mongoOperations.aggregate(typedAggregation, s, aClass);
    }

    public <T> T findById(Object o, Class<T> aClass, String s) {
        return mongoOperations.findById(o, aClass, s);
    }

    public <T> T findAndModify(Query query, Update update, Class<T> aClass) {
        return mongoOperations.findAndModify(query, update, aClass);
    }

    public <T> T findOne(Query query, Class<T> aClass) {
        return mongoOperations.findOne(query, aClass);
    }

    public <T> GeoResults<T> geoNear(NearQuery nearQuery, Class<T> aClass, String s) {
        return mongoOperations.geoNear(nearQuery, aClass, s);
    }

    public <T> T findAndModify(Query query, Update update, FindAndModifyOptions findAndModifyOptions, Class<T> aClass, String s) {
        return mongoOperations.findAndModify(query, update, findAndModifyOptions, aClass, s);
    }

    public <T> DBCollection createCollection(Class<T> aClass) {
        return mongoOperations.createCollection(aClass);
    }

    public <T> T findOne(Query query, Class<T> aClass, String s) {
        return mongoOperations.findOne(query, aClass, s);
    }

    public WriteResult updateFirst(Query query, Update update, Class<?> aClass) {
        return mongoOperations.updateFirst(query, update, aClass);
    }

    public <T> T findAndRemove(Query query, Class<T> aClass, String s) {
        return mongoOperations.findAndRemove(query, aClass, s);
    }

    public IndexOperations indexOps(String s) {
        return mongoOperations.indexOps(s);
    }
}
