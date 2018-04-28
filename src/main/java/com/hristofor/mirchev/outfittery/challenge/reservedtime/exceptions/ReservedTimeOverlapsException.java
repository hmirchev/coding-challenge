package com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReservedTimeOverlapsException extends RuntimeException {

  public ReservedTimeOverlapsException(final Long stylistId) {
    super("Stylist[id: " + stylistId + "] has an overlapping reserved time.");
  }
}
