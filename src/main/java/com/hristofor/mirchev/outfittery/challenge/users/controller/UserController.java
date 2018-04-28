package com.hristofor.mirchev.outfittery.challenge.users.controller;

import com.hristofor.mirchev.outfittery.challenge.users.dtos.DTOtoEntityConverter;
import com.hristofor.mirchev.outfittery.challenge.users.dtos.UserDTO;
import com.hristofor.mirchev.outfittery.challenge.users.repository.User;
import com.hristofor.mirchev.outfittery.challenge.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(description = "Operations regarding the users.")
@RestController
@RequestMapping("/v1")
public class UserController {

  @Autowired
  private UserService userService;

  @ApiOperation(value = "Creates a new user.", response = UserDTO.class)
  @PostMapping(value = "/users", produces = "application/json")
  public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {

    final User transientUser = DTOtoEntityConverter.convert(userDTO);
    final User persistedUser = userService.createUser(transientUser);

    // setting the location header to point to the created user
    final URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}").buildAndExpand(persistedUser.getId()).toUri();

    return ResponseEntity.created(location).body(DTOtoEntityConverter.convert(persistedUser));
  }

  @ApiOperation(value = "Gets a list of all users.", response = List.class)
  @GetMapping(value = "/users", produces = "application/json")
  public List<UserDTO> getAllUsers() {

    return userService.getAllUsers().stream().map(DTOtoEntityConverter::convert)
        .collect(Collectors.toList());
  }

  @ApiOperation(value = "Gets the user with the provided id.", response = UserDTO.class)
  @ApiResponse(code = 404, message = "The user with the provided id could not be found.")
  @GetMapping(value = "/users/{id}", produces = "application/json")
  public UserDTO getUserById(@PathVariable(value = "id") Long userId) {

    final User user = userService.getUserById(userId);
    return DTOtoEntityConverter.convert(user);
  }

  @ApiOperation(value = "Updates the user with the provided id.", response = UserDTO.class)
  @ApiResponse(code = 404, message = "The user with the provided id could not be found.")
  @PutMapping(value = "/users/{id}", produces = "application/json")
  public UserDTO updateUser(@PathVariable(value = "id") Long userId,
      @Valid @RequestBody UserDTO updatedUserDTO) {

    final User transientUpdatedUser = DTOtoEntityConverter.convert(updatedUserDTO);
    final User persistedUpdatedUser = userService.updateUser(userId, transientUpdatedUser);
    return DTOtoEntityConverter.convert(persistedUpdatedUser);
  }

  @ApiOperation(value = "Deletes the user with the provided id.")
  @ApiResponse(code = 404, message = "The user with the provided id could not be found.")
  @DeleteMapping(value = "/users/{id}", produces = "application/json")
  public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {

    userService.delete(userId);
    return ResponseEntity.ok().build();
  }
}
