package com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidStylistStatusException extends RuntimeException {

  public InvalidStylistStatusException(final Long stylistId) {
    super("Stylist[id: " + stylistId + "] has an invalid status for this reserved time.");
  }
}
