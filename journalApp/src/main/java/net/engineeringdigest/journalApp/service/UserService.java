package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.repositery.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.stereotype.Service;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository ;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger  = LoggerFactory.getLogger(UserService.class);

    public void saveNewUser(User user ){
        try{
            //userRepository.save(user );//save is inbuilt method inside inbuilt MongoRepository interface of spring
           // System.out.println("hi iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user );
        } catch (Exception e) {
            logger.error("Duplicate entry of {} :",user.getUserName() ,e );
           // System.out.println("hi iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        }

    }
    public void saveAdmin(User user ){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            System.out.println("saving user as admin");
            userRepository.save(user );
        } catch (Exception e) {
            logger.error("Duplicate entry of {} :",user.getUserName() ,e );
        }

    }

    public void saveUser(User user ){
        try{
          userRepository.save(user);
        } catch (Exception e) {
            logger.error("Duplicate entry of {} :",user.getUserName() ,e );
        }

    }

    public List<User> getAll(){
            System.out.println("Almost there ");
            return userRepository.findAll();

    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id) ;
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id) ;
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }


}
