package com.documentanalyzer.repositories;

import com.documentanalyzer.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User u where u.lastUpload is null or u.lastUpload<:timestamp")
    public Iterable<User> findUsersDidNotUpload(Timestamp timestamp);

    @Query("from User u where u.lastUpload>=:timestamp")
    public Iterable<User> findUsersDidUpload(Timestamp timestamp);

}
