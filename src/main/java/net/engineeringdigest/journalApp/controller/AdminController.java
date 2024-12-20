package net.engineeringdigest.journalApp.controller;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllEntry(), HttpStatus.OK);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody  User user){
        try{
            userService.createAdminUser(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
        }
    }

    @GetMapping("/all-entries")
    public ResponseEntity<?> getAllEntries(){
        return new ResponseEntity<>(journalEntryService.getAllEntry(), HttpStatus.OK);
    }
}
