package com.rjAbhi.journalApp.service;

import com.rjAbhi.journalApp.entity.JournalEntry;
import com.rjAbhi.journalApp.entity.User;
import com.rjAbhi.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public  void saveEntry(JournalEntry journalEntry, String username)
    {
        try{
            User user= userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
//            At first , we have save the journal entries in JournalEntries
            JournalEntry saved= journalEntryRepository.save(journalEntry);
//            At second, we have to saved the journal entries in the user's entries
            user.getJournalEntries().add(saved);
//            then we update the user
            userService.saveUser(user);

        }catch (Exception e){
            log.error("Exception",e);
        }

    }

    public  void saveEntry(JournalEntry journalEntry)
    {
        try{
            journalEntryRepository.save(journalEntry);

        }catch (Exception e){
            log.error("Exception",e);
        }

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();

    }

    public Optional<JournalEntry> findById(ObjectId id){
        return  journalEntryRepository.findById(id);
//        Optional ek box hai jismein data ho skata hai ya nahi bhi ho skata hai
    }

    public void deleteById(ObjectId id, String username)
    {
        User user= userService.findByUsername(username);
        user.getJournalEntries().removeIf(it->it.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }



}
