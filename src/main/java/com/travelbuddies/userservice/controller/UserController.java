package com.travelbuddies.userservice.controller;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.travelbuddies.userservice.entity.User;
import com.travelbuddies.userservice.service.UserService; 

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user) {
        log.info("Inside saveUser of UserController");
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Long userId){
        log.info("Inside findUserById of UserController");
        return userService.findUserById(userId);
    }

}
