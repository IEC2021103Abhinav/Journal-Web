package com.rjAbhi.journalApp.service;

import com.rjAbhi.journalApp.entity.JournalEntry;
import com.rjAbhi.journalApp.entity.User;
import com.rjAbhi.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Service
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
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
            userService.SaveUser(user);

        }catch (Exception e){
            log.error("Exception",e);
            throw new RuntimeException("An error occurred while saving the entry",e);
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

    @Transactional
    public boolean deleteById(ObjectId id, String username)
    {
        boolean removed=false;
        try {
            User user= userService.findByUsername(username);
            removed=user.getJournalEntries().removeIf(it->it.getId().equals(id));
            if(removed)
            {
                userService.SaveUser(user);
                journalEntryRepository.deleteById(id);

            }

        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting journalById");
        }
        return removed;

    }

}
