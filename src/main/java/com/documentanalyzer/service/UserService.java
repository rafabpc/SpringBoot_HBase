package com.documentanalyzer.service;

import com.documentanalyzer.model.User;
import com.documentanalyzer.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    //This service is a representation of the user repository
    //This class is a kind of interceptor of database calls
    //There is only one need to intercept the call and which is saving the user
    //Doing this, it is possible to customize the validation message of mandatory fields

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void saveUser(User user) throws Exception {
        if(user.getTeams() == null || user.getTeams().isEmpty()){
            throw new Exception("An user must have at least one team");
        }
        userRepository.save(user);
    }

}
