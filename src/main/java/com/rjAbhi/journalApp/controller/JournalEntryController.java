package com.rjAbhi.journalApp.controller;

import com.rjAbhi.journalApp.entity.JournalEntry;
import com.rjAbhi.journalApp.entity.User;
import com.rjAbhi.journalApp.service.JournalEntryService;
import com.rjAbhi.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<?> getALlJournalEntriesOfUser(@PathVariable String username){
        User user=userService.findByUsername(username);
        List<JournalEntry>allJournal= user.getJournalEntries();
        if(allJournal!=null && !allJournal.isEmpty())
        {
            return new ResponseEntity<>(allJournal,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String username){
        try{
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry>journalEntry= journalEntryService.findById(myId);
        if(journalEntry.isPresent())
        {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{username}/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId, @PathVariable String username)
    {
//         In this we have to do cascade delete , it means we have to delete the Journal entries
//        for Journal Entry but from user there is no deletion of Journal
//        for that we have to implement
        journalEntryService.deleteById(myId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{username}/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id ,
                                               @RequestBody JournalEntry newEntry,
                                               @PathVariable String username)
    {
        JournalEntry oldEntry= journalEntryService.findById(id).orElse(null);
        if(oldEntry!=null)
        {
            oldEntry.setTitle(newEntry.getTitle()!=null &&
                    !newEntry.getTitle().equals("")?newEntry.getTitle(): oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null &&
                    !newEntry.getContent().equals("")? newEntry.getContent():oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }





}
