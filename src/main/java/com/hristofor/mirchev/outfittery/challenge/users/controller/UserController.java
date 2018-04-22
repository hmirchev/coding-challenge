package com.hristofor.mirchev.outfittery.challenge.users.controller;

import com.hristofor.mirchev.outfittery.challenge.users.repository.User;
import com.hristofor.mirchev.outfittery.challenge.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Operations regarding the users.")
@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Gets a list of all users.", response = List.class)
    @GetMapping(value = "/users", produces = "application/json")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Creates a new user.", response = User.class)
    @PostMapping(value ="/users", produces = "application/json")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @ApiOperation(value = "Gets the uer with the provided id.", response = User.class)
    @GetMapping(value = "/users/{id}", produces = "application/json")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userService.getUserById(userId);
    }

    @ApiOperation(value = "Deletes the user with the provided id.")
    @DeleteMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Updates the user with the provided id.", response = User.class)
    @PutMapping(value = "/users/{id}",  produces = "application/json")
    public User updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User updatedUser) {
        return userService.updateUser(userId, updatedUser);
    }
}
