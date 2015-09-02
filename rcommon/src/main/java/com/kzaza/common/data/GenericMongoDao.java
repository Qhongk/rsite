package com.kzaza.common.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by rick.kong.
 */
public interface GenericMongoDao<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    T findOne(ID id);

    void insert(T t);

    int updateFirst(T t) throws Exception;

    T findAndModify(T t) throws Exception;

    T findAndRemove(T t) throws Exception;

    Page<T> findAll(Pageable pageable, Criteria criteria);
}
