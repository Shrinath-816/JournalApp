package net.engineeringdigest.journalApp.service;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositery.JournalEntryRepository ;
import net.engineeringdigest.journalApp.repositery.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository ;

    @Autowired
    private UserService userService ;

    @Autowired
    private UserRepository userRepository ;

    private static final Logger logger  = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry , String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            System.out.println("Saving in MongoREpository");
            JournalEntry saved = journalEntryRepository.save(journalEntry);//save is inbuilt method inside inbuilt MongoRepository interface of spring
            System.out.println("Saving journal entry to the getJournalEntries of users");
            user.getJournalEntries().add(saved);
            System.out.println("journal Entry Saved");
            userService.saveUser(user ) ;
        } catch (Exception e) {
            logger.info("Entry not saved");
            throw new RuntimeException(e.getMessage());
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        try{

            journalEntryRepository.save(journalEntry  );
        } catch (Exception e) {
            System.out.println("Exception  "+ e);
        }

    }


    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id) ;
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
       try{
           User user = userService.findByUserName(userName);
           removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
           if(removed){
               userService.saveUser(user ) ;
               journalEntryRepository.deleteById(id) ;
           }
       }catch(Exception e){
           throw new RuntimeException("An error occured while deleting" +e) ;
       }
       return removed ;

    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
