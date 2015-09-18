package com.kza.common.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by kza
 */
public class MongoTemplateDao<T, ID extends Serializable> implements GenericMongoDao<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(MongoTemplateDao.class);

    protected Class<T> entityClass;

    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected Class getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    protected String getId(T t) throws IllegalAccessException {
        Field idField = null;
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
            }
        }

        if (idField == null) {
            return null;
        }
        idField.setAccessible(true);
        String result = null;
        try {
            result = idField.get(t).toString();
        } catch (IllegalAccessException e) {
            logger.debug("id field is {}", idField);
            logger.debug("IllegalAccessException", e);
            throw e;
        }
        return result;
    }

    @Override
    public void insert(T t) {
        this.mongoTemplate.insert(t);
    }

    public void insert(List<T> t) {
        this.mongoTemplate.insert(t, getEntityClass());
    }

    /**
     * 根据_id修改T信息，修改注意索引的影响
     *
     * @return 影响的行数
     */
    public int updateFirst(T t) throws Exception {
        DBObject userDBObject = (DBObject) getMongoTemplate().getConverter().convertToMongoType(t);
        Update setUpdate = Update.fromDBObject(new BasicDBObject("$set", userDBObject));
        WriteResult result = getMongoTemplate().updateFirst(query(where("_id").is(getId(t))), setUpdate, getEntityClass());
        return result.getN();
    }

    /**
     * 根据_id修改T信息，修改注意索引的影响
     *
     * @return 更新后的T
     */
    public T findAndModify(T t) throws Exception {
        DBObject userDBObject = (DBObject) getMongoTemplate().getConverter().convertToMongoType(t);
        Update setUpdate = Update.fromDBObject(new BasicDBObject("$set", userDBObject));

        return (T) getMongoTemplate().findAndModify(query(where("_id").is(getId(t))),
                setUpdate, new FindAndModifyOptions().returnNew(true), getEntityClass());
    }

    /**
     * 根据_id删除T信息
     *
     * @return 删除的T
     */
    public T findAndRemove(T t) throws Exception {
        return (T) getMongoTemplate().findAndRemove(query(where("_id").is(getId(t))), getEntityClass());
    }

    @Override
    public Page<T> findAll(Pageable pageable, Criteria criteria) {
        Query query = new Query();
        query.addCriteria(criteria);
        query.with(new PageRequest(pageable.getPageNumber(), pageable.getPageSize())).with(pageable.getSort());
        return new PageImpl<T>(this.mongoTemplate.find(query, getEntityClass()), pageable, count());

    }

    @Override
    public <S extends T> S save(S s) {
        this.mongoTemplate.save(s);
        // TODO
        return s;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public T findOne(ID id) {
        return (T) this.mongoTemplate.findOne(query(where("_id").is(id)), getEntityClass());
    }

    @Override
    public boolean exists(ID id) {
        return this.mongoTemplate.exists(query(where("_id").is(id)), getEntityClass());
    }

    @Override
    public Iterable<T> findAll() {
        return this.mongoTemplate.findAll(getEntityClass());
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> iterable) {
        return null;
    }

    @Override
    public long count() {
        return this.mongoTemplate.count(null, getEntityClass());
    }

    @Override
    public void delete(ID id) {
        this.mongoTemplate.remove(query(where("_id").is(id)), getEntityClass());
    }

    @Override
    public void delete(T t) {
        try {
            this.mongoTemplate.remove(query(where("_id").is(getId(t))), getEntityClass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Iterable<? extends T> iterable) {

    }

    @Override
    public void deleteAll() {
        this.mongoTemplate.remove(null, getEntityClass());
    }

    @Override
    public Iterable<T> findAll(Sort sort) {

        return this.mongoTemplate.find(new Query().with(sort), getEntityClass());
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return findAll(pageable, null);
    }
}
