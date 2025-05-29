package com.jeelsoft.JournalApp.controller;

import com.jeelsoft.JournalApp.entity.User;
import com.jeelsoft.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class    UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> all = userService.getAll();
        if(all!=null && !all.isEmpty()){
            return  new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public void creatUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
        User userInDb = userService.findByUserName(userName);
        if (userInDb!=null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{userid}")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId userid) {
        userService.deleteById(userid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

