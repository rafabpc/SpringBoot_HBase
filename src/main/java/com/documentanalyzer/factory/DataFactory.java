package com.documentanalyzer.factory;

import com.documentanalyzer.model.Team;
import com.documentanalyzer.model.User;
import com.documentanalyzer.repositories.TeamRepository;
import com.documentanalyzer.service.UserService;
import com.documentanalyzer.utils.TimestampConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataFactory implements CommandLineRunner {


    //This class is a facilitation offered by Spring Boot
    //The CommandLineRunner interface is a thread that runs when the application starts
    //It allows the application to make pre configurations
    //And this space is being used to setup the database through Spring CrudRepository

    private final TeamRepository teamRepository;
    private final UserService userService;

    public DataFactory(TeamRepository teamRepository, UserService userService){
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        addTeamsAndUsers();
    }

    private void addTeamsAndUsers() throws Exception {

        //HBase script for the starter status.

        Team teamA = new Team();
        teamA.setTeamName("Team A");

        Team teamB = new Team();
        teamB.setTeamName("Team B");

        Team teamC = new Team();
        teamC.setTeamName("Team C");

        //Add teams to the database
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        teamRepository.save(teamC);

        User user1 = new User();
        user1.setUserName("User 1");
        user1.setEmail("user_1@email.com");
        user1.getTeams().add(teamA);
        user1.setJoinDate(TimestampConverter.getTimestamp("01/07/2020"));

        User user2 = new User();
        user2.setUserName("User 2");
        user2.setEmail("user_2@email.com");
        user2.getTeams().add(teamA);
        user2.setJoinDate(TimestampConverter.getTimestamp("03/07/2020"));

        User user3 = new User();
        user3.setUserName("User 3");
        user3.setEmail("user_3@email.com");
        user3.getTeams().add(teamA);
        user3.setJoinDate(TimestampConverter.getTimestamp("03/07/2020"));

        User user4 = new User();
        user4.setUserName("User 4");
        user4.setEmail("user_4@email.com");
        user4.getTeams().add(teamB);
        user4.setJoinDate(TimestampConverter.getTimestamp("30/06/2020"));

        User user5 = new User();
        user5.setUserName("User 5");
        user5.setEmail("user_5@email.com");
        user5.getTeams().add(teamC);
        user5.setJoinDate(TimestampConverter.getTimestamp("30/06/2020"));

        //Add users to the database
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
        userService.saveUser(user5);

        //The many to many (users and teams) is done by JPA in a table defined in the user model.

    }

}