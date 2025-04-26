package net.engineeringdigest.journalApp.controller;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repositery.UserRepository;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private WeatherService weatherService ;

    @GetMapping
    public List<User> getAllUsers(){
            System.out.println("Getting all the users");
            return userService.getAll();

    }



    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb =  userService.findByUserName(userName);
           userInDb.setUserName(user.getUserName());
           userInDb.setPassword(user.getPassword());
           userService.saveNewUser(userInDb) ;

       return new ResponseEntity<>(HttpStatus.NO_CONTENT );
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName() );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT );
    }

    @GetMapping("/greet")
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(weatherResponse != null){
            greeting =  " Tempeature is : " + weatherResponse.getCurrent().getFeelslike() ;
            System.out.println(greeting );

        }else{
            System.out.println("weatherResponse is null");
        }
        return new ResponseEntity<>("Hiiii  "+  authentication.getName()+ greeting ,HttpStatus.OK );
    }


}
