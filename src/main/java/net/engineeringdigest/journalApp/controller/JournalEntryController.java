package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal/api")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    private JournalEntry myEntry;

    @GetMapping
    public ResponseEntity<?>  getAllJournalEntriesOfUser(){
        try{
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            List<JournalEntry> allEntriesOfUser= journalEntryService.getEntriesOfUser(userName);
            return new ResponseEntity<>(allEntriesOfUser,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEntry(@RequestBody JournalEntry entry){
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            JournalEntry savedEntry =journalEntryService.saveEntry(entry,userName);
            return new ResponseEntity<>(savedEntry,HttpStatus.CREATED);

        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/{id}")
    public  ResponseEntity<?> deleteEntry(@PathVariable ObjectId id){
        try{
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.deleteEntry(userName,id);
            return new ResponseEntity<>("Entry deleted",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable String id, @RequestBody JournalEntry updatedEntry){
        try{
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            ObjectId objectId= new ObjectId(id);
            return new ResponseEntity<>(journalEntryService.updateEntry(objectId,userName,updatedEntry),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry){
//        boolean isDone = journalEntryService.updateEntry(id,updatedEntry);
//        if(isDone){
//            return new ResponseEntity<>(updatedEntry,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<?>getEntryById(@PathVariable ObjectId id){
//        Optional<JournalEntry> journalEntry= journalEntryService.findEntryById(id);
//        if(journalEntry.isPresent()){
//            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}
