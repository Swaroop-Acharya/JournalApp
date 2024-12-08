package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImp {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> findAll() {
       Criteria criteria = Criteria.where("userName").is("Swaroop");
        Query query = new Query(criteria);
        return mongoTemplate.find(query,User.class);
    }
}
