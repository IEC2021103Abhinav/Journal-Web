package com.rjAbhi.journalApp.repository;

import com.rjAbhi.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
//    it provides abstraction, to interact with  mongo database configured by springBoot

    public List<User> getUserForSentimentAnalysis(){
//        Kya criteria honi chahiye jisse ham wo user ko select kr lenge
//        Criteria using Query
        Query query= new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
//        yahan pe automatically and laga hua hai dono criteria pe
//        agar hame or lagana hoga tooo
//        Criteria criteria= new Criteria();
//        query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true)
//                ,Criteria.where("sentimentAnalysis").is(true)));

        List<User>users=mongoTemplate.find(query,User.class);
        return users;
    }

}
