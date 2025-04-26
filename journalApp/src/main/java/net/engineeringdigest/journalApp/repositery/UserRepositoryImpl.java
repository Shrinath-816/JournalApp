package net.engineeringdigest.journalApp.repositery;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.regex.Pattern ;
import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate ;

    public List<User> getUsersForSA(){
     Query query = new Query() ;

     //query.addCriteria(Criteria.where("email").exists(true).ne(null).ne("") );
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
     //query.addCriteria(Criteria.where("email").ne(null).ne(""));
     query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
     List<User>  users = mongoTemplate.find(query, User.class);
     for(User u:users){
         System.out.println(u.getUserName() );
         System.out.println(u.getEmail() );
     }
        return users;
    }

}
