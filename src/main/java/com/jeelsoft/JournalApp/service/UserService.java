package com.jeelsoft.JournalApp.service;

import com.jeelsoft.JournalApp.entity.User;
import com.jeelsoft.JournalApp.repository.UserRepository;
import com.jeelsoft.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User User){
        userRepository.save(User);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
         userRepository.deleteById(id);
    }
    public User findByUserName(String userName){
        return  userRepository.findByUserName(userName);
    }

}
