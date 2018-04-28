package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.lang.NonNull;

public interface ReservedTimeService {

  /**
   * Gets a list of all {@link ReservedTime} between the provided [start, end] range.
   *
   * @param start the start period for the overlap. Inclusive.
   * @param end the end period for the overlap. Inclusive.
   * @return a list of all {@link ReservedTime} between the provided [start, end] range, or empty
   * list, if there are none.
   */
  @NonNull
  List<ReservedTime> getAllReservedTimeBetween(@NonNull OffsetDateTime start,
      @NonNull OffsetDateTime end);

  /**
   * Gets a list of all {@link ReservedTime} of the stylist with the provided id, that overlap with
   * the provided [start, end] range. If none overlap, returns an empty list.
   *
   * @param stylistId the id of the stylist, whose overlapping {@link ReservedTime} we want to get.
   * @param start the start period for the overlap. Inclusive.
   * @param end the end period for the overlap. Inclusive.
   * @return a list of all {@link ReservedTime} of the stylist with the provided id, that overlap
   * with the provided [start, end] range.
   */
  @NonNull
  List<ReservedTime> getReservedTimesOfStylistThatOverlapWith(@NonNull Long stylistId,
      @NonNull OffsetDateTime start,
      @NonNull OffsetDateTime end);
}
