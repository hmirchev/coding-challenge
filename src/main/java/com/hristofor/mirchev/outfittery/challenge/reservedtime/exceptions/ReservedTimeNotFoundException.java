package com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ReservedTimeNotFoundException extends RuntimeException {

  public ReservedTimeNotFoundException(final Long reservedTimeId) {
    super("ReservedTime[id: " + reservedTimeId + "] was not found.");
  }
}
