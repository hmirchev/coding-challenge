package com.hristofor.mirchev.outfittery.challenge.stylists.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StylistNotFoundException extends RuntimeException  {

    public StylistNotFoundException(final Long stylistId) {
        super("Stylist[id: " + stylistId + "] was not found.");
    }
}
