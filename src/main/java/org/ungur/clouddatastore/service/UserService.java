package org.ungur.clouddatastore.service;

import com.google.cloud.datastore.*;
import org.springframework.scheduling.annotation.Async;
import org.ungur.clouddatastore.model.BatchUser;
import org.ungur.clouddatastore.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    Datastore datastore;

    private KeyFactory userKeyFactory;

    @PostConstruct
    public void initializeKeyFactories() {
        log.info("Initializing key factories");
        userKeyFactory = datastore.newKeyFactory().kind("User");
    }

    public Entity createUser(User user) {
        return datastore.put(createUserEntity(user));
    }

    public Batch.Response createUser(BatchUser users) {
        Batch batch = datastore.newBatch();
        for (User user : users.getUsers()) {
            batch.put(createUserEntity(user));
        }

        return batch.submit();
    }

    private Entity createUserEntity(User user) {
        Key key = userKeyFactory.newKey(user.getId());
        return Entity.builder(key)
                .set("email", user.getEmail())
                .set("password", user.getPassword())
                .set("fullName", user.getFullName())
                .set("age", user.getAge())
                .build();
    }

    @Async
    public void updateUser(String id, User user) {
        //
    }

    @Async
    public void deleteUser(String id) {
        //
    }

}
