package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedTimeRepository extends JpaRepository<ReservedTime, Long> {

  /**
   * Selects all {@link ReservedTime} in the provided [start, end] range.
   *
   * @param start the start period. Inclusive.
   * @param end the end period. Inclusive.
   * @return a list of all {@link ReservedTime} in the provided [start, end] range.
   */
  @Query("SELECT rt FROM ReservedTime rt WHERE rt.start >= :start AND rt.end <= :end")
  List<ReservedTime> getReservedTimesBetween(@Param("start") OffsetDateTime start,
      @Param("end") OffsetDateTime end);

  /**
   * Selects all {@link ReservedTime} of the stylist with the provided id, <br/> that overlap with
   * the provided [start, end] range (i.e. StartA <= EndB && EndA >= StartB)
   *
   * @param stylistId the id of the stylist, whose overlapping {@link ReservedTime} we want to get.
   * @param start the start period for the overlap. Inclusive.
   * @param end the end period for the overlap. Inclusive.
   * @return a list of all {@link ReservedTime} of the stylist with the provided id, that overlap
   * with the provided [start, end] range.
   */
  @Query("SELECT rt FROM ReservedTime rt WHERE rt.stylistId = :stylistId AND rt.start <= :end AND rt.end >= :start")
  List<ReservedTime> getReservedTimesOfStylistThatOverlapWith(@Param("stylistId") Long stylistId,
      @Param("start") OffsetDateTime start,
      @Param("end") OffsetDateTime end);
}
