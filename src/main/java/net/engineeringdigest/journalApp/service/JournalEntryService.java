package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Cacheable(key = "#userName" ,value = "userJournalEntries")
    public List<JournalEntry> getEntriesOfUser(String userName) throws UserNotFoundException{
        Optional<User> userInDb= userService.findByUserName(userName);
        if(!userInDb.isPresent()){
            throw new UserNotFoundException("User not found with the User name: "+ userName);
        }
        log.info("Getting the data from db");
        return userInDb.get().getJournalEntries();
    }

    @Transactional
    @CachePut(value = "userJournalEntries" , key="#userName")
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) throws UserNotFoundException {
        JournalEntry saveEntry=journalEntryRepository.save(journalEntry);
        Optional<User> user=userService.findByUserName(userName);
        User existingUser = user.get();

        // Initialize the list if it is null
        if(existingUser.getJournalEntries()==null)existingUser.setJournalEntries(new ArrayList<>());
        existingUser.getJournalEntries().add(saveEntry);
        userService.saveEntry(user.get());
        return saveEntry;
    }

    @Cacheable(key="'all'",value = "allJournalEntries")
    public List<JournalEntry> getAllEntry() {
        log.warn("Getting all journal entries for admin from the DB.");
        List<JournalEntry> entries = journalEntryRepository.findAll();
        log.debug("Fetched entries: {}", entries);  // Add logging to check the returned value
        return entries;
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
    @Caching(evict = {
        @CacheEvict(value = "allJournalEntries", allEntries = true),
        @CacheEvict(value = "userJournalEntries", key = "#userName")
    })
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

    @CachePut(value = "userJournalEntries" , key = "#userName")
    public JournalEntry updateEntry(ObjectId id, String userName, JournalEntry updatedEntry) throws UserNotFoundException {
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
        return updatedEntry;
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
