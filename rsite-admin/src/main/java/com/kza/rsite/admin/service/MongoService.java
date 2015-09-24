package com.kza.rsite.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kza on 2015/9/18.
 */
@Component
public class MongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void queryUser() {
        List<Person> users = mongoTemplate.findAll(Person.class, "User");
        for (Person user : users) {
            System.out.println(user + "***************************************");
        }
    }

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
}
