package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repositery.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService ;
    @Autowired
    private UserRepositoryImpl userRepository ;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private AppCache appCache ;


  //  @Scheduled(cron = "0 0 9 * * SUN")//you can make cron expression using cronmaker websites
    public void fetchUsersAndSendSAMails(){
        List<User> users = userRepository.getUsersForSA();
        for(User user:users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment > sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());//these are the journal entries of last 7 days
            Map<Sentiment ,Integer > sentTimeCounts = new HashMap<>();
            for(Sentiment sentiment :sentiments){
                if(sentiment != null){
                    sentTimeCounts.put(sentiment ,sentTimeCounts.getOrDefault(sentiment ,0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment ,Integer > entry:sentTimeCounts.entrySet() ){
                if(entry.getValue() > maxCount ){
                    maxCount =entry.getValue() ;
                    mostFrequentSentiment = entry.getKey() ;
                }
            }
            if(mostFrequentSentiment != null){
                emailService.setJavaMailSender(user.getEmail() ,"Sentiment for the last 7 days",mostFrequentSentiment.toString() );
            }


        }
    }


  //  @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init() ;
    }

}
