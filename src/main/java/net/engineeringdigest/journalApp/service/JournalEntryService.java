package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Cacheable(key = "#userName" ,value = "entriesOfUser")
    public List<JournalEntry> getEntriesOfUser(String userName) throws UserNotFoundException{
        Optional<User> userInDb= userService.findByUserName(userName);
        if(!userInDb.isPresent()){
            throw new UserNotFoundException("User not found with the User name: "+ userName);
        }
        log.info("Getting the data from db");
        return userInDb.get().getJournalEntries();
    }

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) throws UserNotFoundException {
        return userService.findByUserName(userName).map(user -> {
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveEntry(user);
            return journalEntry;
        }).orElseThrow(()-> new UserNotFoundException("User name not found "+ userName));
    }

    public List<JournalEntry> getAllEntry(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }


    public boolean deleteEntryById(ObjectId id){
        Optional<JournalEntry> entry = journalEntryRepository.findById(id);

        if (entry.isPresent()) {
            journalEntryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deleteEntry(String userName,ObjectId id) throws  UserNotFoundException{
        Optional<User> userInDb= userService.findByUserName(userName);
        Optional<JournalEntry> entryInDb= journalEntryRepository.findById(id);
        if(!userInDb.isPresent()){
            throw new UserNotFoundException("User not found with the User name: "+ userName);
        }

        if(!entryInDb.isPresent()){
            throw new IllegalArgumentException("No entry exists with the Id: "+id );
        }
        userInDb.get().getJournalEntries().removeIf(x ->x.getId().equals(id));
        journalEntryRepository.deleteById(id);
        userService.saveEntry(userInDb.get());
    }


    public void updateEntry(ObjectId id, String userName, JournalEntry updatedEntry) throws UserNotFoundException {
        Optional<User> userInDb = userService.findByUserName(userName);
        if (!userInDb.isPresent()) {
            throw new UserNotFoundException("User not found with the User name: " + userName);
        }
        Optional<JournalEntry> oldEntry = journalEntryRepository.findById(id);
        if (!oldEntry.isPresent()) {
            throw new IllegalArgumentException("No entry exists with the Id: " + id);
        }
        JournalEntry entry = oldEntry.get();
        if (updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty()) {
            entry.setContent(updatedEntry.getContent());
        }
        if (updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()) {
            entry.setTitle(updatedEntry.getTitle());
        }
        journalEntryRepository.save(entry);
    }


//    public boolean updateEntry(ObjectId id, JournalEntry newEntry){
//        Optional<JournalEntry> oldEntry=journalEntryRepository.findById(id);
//        if(oldEntry.isPresent()){
//           JournalEntry entry=oldEntry.get();
////            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
////                entry.setContent(newEntry.getContent());
////            }
////            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
////                entry.setTitle(newEntry.getTitle());
//            }
//            saveEntry(entry);
//            return true;
//        }
//        return false;
//    }

}
