package com.hristofor.mirchev.outfittery.challenge.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(final Long userId) {
    super("User[id: " + userId + "] was not found.");
  }
}
