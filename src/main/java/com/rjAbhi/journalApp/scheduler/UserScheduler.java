package com.rjAbhi.journalApp.scheduler;

import com.rjAbhi.journalApp.cache.AppCache;
import com.rjAbhi.journalApp.entity.JournalEntry;
import com.rjAbhi.journalApp.entity.User;
import com.rjAbhi.journalApp.repository.UserRepositoryImpl;
import com.rjAbhi.journalApp.service.EmailService;
import com.rjAbhi.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSentimentalAnalysisMail(){
        List<User> users=userRepositoryImpl.getUserForSentimentAnalysis();
        for(User user: users)
        {
            List<JournalEntry>journalEntries=user.getJournalEntries();
            List<String>filteredEntries= journalEntries.stream().filter(
                    it->it.getDate().isAfter(
                            LocalDateTime.now().minus(
                                    7, ChronoUnit.DAYS))
            ).map(x->x.getContent()).collect(Collectors.toList());

            String entry= String.join(" ",filteredEntries);
             String sentiment=sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days",sentiment);
        }

    }

    @Scheduled(cron="0 0/10 * ? * *")
    public  void clearAppCache(){
        appCache.init();
    }

}
