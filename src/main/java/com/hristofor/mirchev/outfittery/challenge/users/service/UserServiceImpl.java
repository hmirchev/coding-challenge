package com.hristofor.mirchev.outfittery.challenge.users.service;

import com.hristofor.mirchev.outfittery.challenge.users.exceptions.UserNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.users.repository.User;
import com.hristofor.mirchev.outfittery.challenge.users.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  @NonNull
  public User createUser(@NonNull final User user) {
    Objects.requireNonNull(user, "User should not be null here.");

    log.debug("Creating a user");
    return userRepository.saveAndFlush(user);
  }

  @Override
  @NonNull
  public List<User> getAllUsers() {
    log.debug("Getting all users");
    return userRepository.findAll();
  }

  @Override
  @NonNull
  public User getUserById(@NonNull final Long userId) {
    Objects.requireNonNull(userId, "UserId should not be null here.");

    log.debug("Getting a user with id " + userId);
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

  @Override
  @NonNull
  public User updateUser(@NonNull final Long userId, @NonNull final User updatedUser) {
    Objects.requireNonNull(userId, "UserId should not be null here.");
    Objects.requireNonNull(updatedUser, "User should not be null here.");

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    user.setEmail(updatedUser.getEmail());
    user.setPassword(updatedUser.getPassword());
    user.setFirstName(updatedUser.getFirstName());
    user.setLastName(updatedUser.getLastName());
    user.setBirthday(updatedUser.getBirthday());
    user.setPhone(updatedUser.getPhone());
    user.setAddress(updatedUser.getAddress());
    user.setTimeZone(updatedUser.getTimeZone());

    log.debug("Updating a user with id " + userId);
    return userRepository.saveAndFlush(user);
  }

  @Override
  public void delete(@NonNull final Long userId) {
    Objects.requireNonNull(userId, "UserId should not be null here.");

    final User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    log.debug("Deleting a user with id " + userId);
    userRepository.delete(user);
  }
}
