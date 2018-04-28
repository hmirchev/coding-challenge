package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTimeRepository;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ReservedTimeServiceImpl implements ReservedTimeService {

  private static final Logger log = LoggerFactory.getLogger(ReservedTimeServiceImpl.class);

  @Autowired
  private ReservedTimeRepository reservedTimeRepository;

  @Override
  @NonNull
  public List<ReservedTime> getAllReservedTimeBetween(@NonNull final OffsetDateTime start,
      @NonNull final OffsetDateTime end) {
    Objects.requireNonNull(start, "Start OffsetDateTime should not be null here.");
    Objects.requireNonNull(start, "End OffsetDateTime should not be null here.");

    log.debug("Getting all reserved times in a provided range.");

    final List<ReservedTime> reservedTimesBetween = reservedTimeRepository
        .getReservedTimesBetween(start, end);

    return reservedTimesBetween != null ? reservedTimesBetween : Collections.emptyList();
  }

  @Override
  @NonNull
  public List<ReservedTime> getReservedTimesOfStylistThatOverlapWith(@NonNull final Long stylistId,
      @NonNull final OffsetDateTime start,
      @NonNull final OffsetDateTime end) {
    Objects.requireNonNull(stylistId, "StylistId should not be null here.");
    Objects.requireNonNull(start, "Start OffsetDateTime should not be null here.");
    Objects.requireNonNull(end, "End OffsetDateTime should not be null here.");

    log.debug("Getting all reserved times for stylist " + stylistId + " in range.");

    final List<ReservedTime> timesThatOverlap = reservedTimeRepository
        .getReservedTimesOfStylistThatOverlapWith(stylistId, start, end);

    return timesThatOverlap != null ? timesThatOverlap : Collections.emptyList();
  }
}
