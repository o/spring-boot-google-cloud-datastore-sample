package org.ungur.clouddatastore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ungur.clouddatastore.model.BatchUser;
import org.ungur.clouddatastore.model.Message;
import org.ungur.clouddatastore.model.User;
import org.ungur.clouddatastore.service.UserService;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Upsert new user
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Message> addUser(@Valid @RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok().body(new Message("Created"));
    }

    /**
     * Upsert new users
     *
     * @param users
     * @return
     */
    @RequestMapping(value = "/users/batch", method = RequestMethod.POST)
    public ResponseEntity<Message> addUsers(@Valid @RequestBody BatchUser users) {
        userService.createUser(users);
        return ResponseEntity.ok().body(new Message("Batch created: " + users.getUsers().size()));
    }

    /**
     * Update existing user
     *
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    private ResponseEntity updateUser(@PathVariable("id") String id, @Valid @RequestBody User user) {
        userService.updateUser(id, user);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a user
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    private ResponseEntity deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
