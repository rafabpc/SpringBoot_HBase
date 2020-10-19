package com.documentanalyzer.controller;

import com.documentanalyzer.model.User;
import com.documentanalyzer.repositories.UserRepository;
import com.documentanalyzer.utils.TimestampConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class UserController {

    //The user controller provides all endpoints regarding the user model (All methods can be accessed by Postman)

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //This method retrieves all stored users doing join in teams and uploads
    @RequestMapping("/users")
    public @ResponseBody  String getUsers() throws JsonProcessingException {
        Iterable<User> usersIterator = userRepository.findAll();
        List<User> users = StreamSupport
                .stream(usersIterator.spliterator(), false)
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);

        return json;
    }

    //This method retrieves all users that did not upload in a period of time (today - numberOfDays)
    @RequestMapping("/usersDidNotUpload/{numberOfDays}")
    public @ResponseBody  String getUsersDidNotUploadInAPeriodOfTime(@PathVariable Integer numberOfDays) throws JsonProcessingException {
        Timestamp timestamp = TimestampConverter.getTimestampTodaySubtractedByDays(numberOfDays);

        Iterable<User> userIterator = userRepository.findUsersDidNotUpload(timestamp);
        List<User> uploads = StreamSupport
                .stream(userIterator.spliterator(), false)
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(uploads);

        return json;
    }

    //This method retrieves all users that did upload in a period of time (today - numberOfDays)
    @RequestMapping("/usersDidUpload/{numberOfDays}")
    public @ResponseBody  String getUsersDidUploadInAPeriodOfTime(@PathVariable Integer numberOfDays) throws JsonProcessingException {
        Timestamp timestamp = TimestampConverter.getTimestampTodaySubtractedByDays(numberOfDays);

        Iterable<User> userIterator = userRepository.findUsersDidUpload(timestamp);
        List<User> uploads = StreamSupport
                .stream(userIterator.spliterator(), false)
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(uploads);

        return json;
    }

}
