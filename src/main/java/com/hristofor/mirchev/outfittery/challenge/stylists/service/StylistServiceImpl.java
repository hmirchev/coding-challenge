package com.hristofor.mirchev.outfittery.challenge.stylists.service;

import com.hristofor.mirchev.outfittery.challenge.stylists.exceptions.InvalidStylistStatusTransitionException;
import com.hristofor.mirchev.outfittery.challenge.stylists.exceptions.StylistNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.Stylist;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistRepository;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatusTransitionValidator;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class StylistServiceImpl implements StylistService {

  private static final Logger log = LoggerFactory.getLogger(StylistServiceImpl.class);

  @Autowired
  private StylistRepository stylistRepository;

  @Override
  @NonNull
  public Stylist createStylist(@NonNull final Stylist stylist) {
    Objects.requireNonNull(stylist, "Stylist should not be null here.");

    log.debug("Creating a stylist");
    return stylistRepository.saveAndFlush(stylist);
  }

  @Override
  @NonNull
  public List<Stylist> getAllStylists() {
    log.debug("Getting all stylists");
    return stylistRepository.findAll();
  }

  @Override
  @NonNull
  public Stylist getStylistById(@NonNull final Long stylistId) {
    Objects.requireNonNull(stylistId, "StylistId should not be null here.");

    log.debug("Returning a stylist with id " + stylistId);

    return stylistRepository.findById(stylistId)
        .orElseThrow(() -> new StylistNotFoundException(stylistId));
  }

  @Override
  @NonNull
  public List<Stylist> getStylistsByIds(@NonNull final List<Long> stylistIds) {
    Objects.requireNonNull(stylistIds, "The list of stylist ids should not be null here.");

    log.debug("Returning a list of stylists based on the provided ids");

    return stylistRepository.findAllById(stylistIds);
  }

  @Override
  @NonNull
  public Stylist updateStylist(@NonNull final Long stylistId,
      @NonNull final Stylist updatedStylist) {
    Objects.requireNonNull(stylistId, "StylistId should not be null here.");
    Objects.requireNonNull(updatedStylist, "Stylist should not be null here.");

    Stylist stylist = stylistRepository.findById(stylistId)
        .orElseThrow(() -> new StylistNotFoundException(stylistId));

    stylist.setFirstName(updatedStylist.getFirstName());
    stylist.setLastName(updatedStylist.getLastName());
    stylist.setStatus(updatedStylist
        .getStatus()); // Since it is a semantic "replace", I shouldn't validate the transition change
    stylist.setTimeZone(updatedStylist.getTimeZone());

    log.debug("Updating a stylist with id " + stylistId);
    return stylistRepository.saveAndFlush(stylist);
  }

  @Override
  @NonNull
  public Stylist changeStylistStatus(@NonNull final Long stylistId,
      @NonNull final StylistStatus updatedStylistStatus) {
    Objects.requireNonNull(stylistId, "StylistId should not be null here.");
    Objects.requireNonNull(updatedStylistStatus, "StylistStatus should not be null here.");

    Stylist stylist = stylistRepository.findById(stylistId)
        .orElseThrow(() -> new StylistNotFoundException(stylistId));

    if (!StylistStatusTransitionValidator
        .isTransitionValid(stylist.getStatus(), updatedStylistStatus)) {

      throw new InvalidStylistStatusTransitionException(stylistId, updatedStylistStatus);
    }
    stylist.setStatus(updatedStylistStatus);

    log.debug("Changing the status of a stylist with id " + stylistId);
    return stylistRepository.saveAndFlush(stylist);
  }

  @Override
  public void deleteStylist(@NonNull final Long stylistId) {
    Objects.requireNonNull(stylistId, "StylistId should not be null here.");

    final Stylist user = stylistRepository.findById(stylistId)
        .orElseThrow(() -> new StylistNotFoundException(stylistId));

    log.debug("Deleting a stylist with id " + stylistId);
    stylistRepository.delete(user);
  }
}
