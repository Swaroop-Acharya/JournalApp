package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user){
        userRepository.save(user);

    }


    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);

    }
    public List<User> getAllEntry(){
        return userRepository.findAll();
    }
    public Optional<User> findEntryById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteUserByUserName(String userName) {
        userRepository.deleteUserByUserName(userName);
    }

    public User updateEntry(String username,String password) {
        User userInDb = userRepository.findByUserName(username);
        userInDb.setPassword(password);
        saveNewUser(userInDb);
        return userInDb;
    }

    public Optional<User> findByUserName(String userName){
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    public void createAdminUser(User user){
        user.setRoles(Arrays.asList("USER","ADMIN"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
