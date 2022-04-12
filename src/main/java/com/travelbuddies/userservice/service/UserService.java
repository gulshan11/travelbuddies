package com.travelbuddies.userservice.service;

import com.travelbuddies.userservice.entity.User;
import com.travelbuddies.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    public User findUserById(Long userId){
        log.info("Inside findUserById of UserService");
        return userRepository.findByUserId(userId);

    }

}
