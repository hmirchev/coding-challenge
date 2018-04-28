package com.hristofor.mirchev.outfittery.challenge.stylists.exceptions;

import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidStylistStatusTransitionException extends RuntimeException {

  public InvalidStylistStatusTransitionException(final Long stylistId,
      final StylistStatus updatedStylistStatus) {
    super("Stylist[id: " + stylistId + "] can not be updated to " + updatedStylistStatus);
  }
}
