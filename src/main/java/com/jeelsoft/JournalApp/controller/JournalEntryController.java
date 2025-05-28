package com.jeelsoft.JournalApp.controller;

import com.jeelsoft.JournalApp.entity.JournalEntry;
import com.jeelsoft.JournalApp.entity.User;
import com.jeelsoft.JournalApp.service.JournalEntryService;
import com.jeelsoft.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllEntriesOfUser(@PathVariable String userName){
        User user= userService.findByUserName(userName);
        List<JournalEntry> all =user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return  new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){
        try {
            User user= userService.findByUserName(userName);
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName){
        journalEntryService.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("/id/{myId}")
//    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry) {
//        JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);
//        if (oldEntry != null) {
//            // Only update title if it's not null or empty
//            if (newEntry.getTitle() != null && !newEntry.getTitle().trim().isEmpty()) {
//                oldEntry.setTitle(newEntry.getTitle());
//            }
//
//            /* Only update content if it's not null or empty */
//            if (newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty()) {
//                oldEntry.setContent(newEntry.getContent());
//                return  new ResponseEntity<>(oldEntry,HttpStatus.OK);
//            }
//
//            journalEntryService.saveEntry(oldEntry, user);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}

