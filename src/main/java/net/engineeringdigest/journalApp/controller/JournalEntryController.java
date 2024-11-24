package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{userName}")
    public ResponseEntity<?>  getAllJournalEntriesOfUser(@PathVariable String userName){
        try{
            List<JournalEntry> allEntriesOfUser= journalEntryService.getEntriesOfUser(userName);
            return new ResponseEntity<>(allEntriesOfUser,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<?>getEntryById(@PathVariable ObjectId id){
//        Optional<JournalEntry> journalEntry= journalEntryService.findEntryById(id);
//        if(journalEntry.isPresent()){
//            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PostMapping("/add/{userName}")
    public ResponseEntity<?> addEntry(@RequestBody JournalEntry entry,@PathVariable String userName){
        try {
            JournalEntry savedEntry =journalEntryService.saveEntry(entry,userName);
            return new ResponseEntity<>(savedEntry,HttpStatus.CREATED);

        }catch (Exception e) {
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
    @DeleteMapping("delete/{userName}/{id}")
    public  ResponseEntity<?> deleteEntry(@PathVariable String userName,@PathVariable ObjectId id){
        try{
            journalEntryService.deleteEntry(userName,id);
            return new ResponseEntity<>("Entry deleted",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("update/{userName}/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @PathVariable String userName,@RequestBody JournalEntry updatedEntry){
        try{
           journalEntryService.updateEntry(id,userName,updatedEntry);
            return new ResponseEntity<>(updatedEntry,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
