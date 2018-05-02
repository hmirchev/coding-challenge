package com.hristofor.mirchev.outfittery.challenge.reservedtime.controller;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.DTOtoEntityConverter;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.ReservedTimeDTO;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.ReservedTimeService;
import com.hristofor.mirchev.outfittery.challenge.stylists.controller.StylistController;
import com.hristofor.mirchev.outfittery.challenge.stylists.dtos.StylistDTO;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import com.hristofor.mirchev.outfittery.challenge.stylists.service.StylistServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Operations regarding all reserved times.")
@RestController
@RequestMapping("/v1")
public class ReservedTimeController {

  private static final Logger log = LoggerFactory.getLogger(StylistServiceImpl.class);

  @Autowired
  private ReservedTimeService reservedTimeService;

  @Autowired
  private StylistController stylistController;

  @ApiOperation(value = "Gets a list of all reserved times between the provided range.", response = List.class)
  @GetMapping(value = "/reservedTimes", produces = "application/json")
  public List<ReservedTimeDTO> getAllReservedTimesBetween(
      @RequestParam(value = "start") String start,
      @RequestParam(value = "end") String end) {

    final OffsetDateTime startTime = OffsetDateTime.parse(start);
    final OffsetDateTime endTime = OffsetDateTime.parse(end);

    final List<ReservedTime> reservedTimesBetween = reservedTimeService
        .getAllReservedTimeBetween(startTime, endTime);

    return reservedTimesBetween.stream().map(DTOtoEntityConverter::convert)
        .collect(Collectors.toList());
  }

  /**
   * I don't believe in doing this method. I think it breaks the REST representation, as "available
   * time" is not a resource we operate with, we operate with the inverse.
   */
  @ApiOperation(value = "Gets a list of all available times for an appointment between the provided range.", response = List.class)
  @GetMapping(value = "/availableTimes", produces = "application/json")
  public List<ReservedTimeDTO> getAllAvailableTimesBetween(
      @RequestParam(value = "start") String start,
      @RequestParam(value = "end") String end) {

    final OffsetDateTime startTime = OffsetDateTime.parse(start);
    final OffsetDateTime endTime = OffsetDateTime.parse(end);

    final List<ReservedTime> reservedTimesBetween = reservedTimeService
        .getAllReservedTimeBetween(startTime, endTime);

    final Predicate<StylistDTO> readyToStyleStatus = stylistDTO -> stylistDTO.getStatus()
        == StylistStatus.READY_TO_STYLE;

    final List<ReservedTime> reservedTimesOfReadyStylists = filterReservedTimesBasedOnStylistWith(
        reservedTimesBetween,
        readyToStyleStatus);

    final List<ReservedTimeDTO> availableTimes = new ArrayList<>();
    for (OffsetDateTime i = startTime; i.isBefore(endTime); i = i.plusMinutes(30)) {

      // find reserved times that overlap with the current time slot: [i, i.plusMinutes(30)]
      final OffsetDateTime currentStartTime = i;
      final OffsetDateTime currentEndTime = i.plusMinutes(30);

      final Predicate<ReservedTime> overlappingTime = reservedTime ->
          reservedTime.getEnd().isAfter(currentStartTime) && reservedTime.getStart()
              .isBefore(currentEndTime);

      final List<ReservedTime> overlappedReservedTimes = reservedTimesOfReadyStylists.stream()
          .filter(overlappingTime)
          .collect(Collectors.toList());

      if (overlappedReservedTimes.isEmpty()) {
        availableTimes.add(new ReservedTimeDTO().setStart(currentStartTime).setEnd(currentEndTime));
      }
    }

    return availableTimes;
  }

  /**
   * Takes a list of reserved times and a predicate on the {@link StylistDTO} and filters those
   * reserved times, whose stylist fulfil the predicate.
   *
   * @param reservedTimes a list of {@link ReservedTime}
   * @param predicate a predicate on the {@link StylistDTO} who we want to be fulfilled.
   * @return a filtered list of {@link ReservedTime}.
   */
  private List<ReservedTime> filterReservedTimesBasedOnStylistWith(
      @NonNull final List<ReservedTime> reservedTimes,
      @NonNull final Predicate<StylistDTO> predicate) {
    Objects.requireNonNull(reservedTimes, "The reserved times should not be null here.");
    Objects.requireNonNull(predicate, "The predicate should not be null here.");

    // ids of the stylists that have the provided reserved times
    final Set<Long> stylistIds = reservedTimes.stream().map(ReservedTime::getStylistId)
        .collect(Collectors.toSet());

    // simulating the call to the stylist microservice here
    final List<StylistDTO> stylists = stylistController
        .getStylistsByIds(new ArrayList<>(stylistIds));

    // filter the stylists with the predicate and gather their ids
    final Set<Long> filteredOutStylistsIds = stylists.stream()
        .filter(predicate).map(StylistDTO::getId).collect(Collectors.toSet());

    // return just those reserved times that have a stylist id among the filtered out
    return reservedTimes.stream()
        .filter(reservedTime -> filteredOutStylistsIds.contains(reservedTime.getStylistId()))
        .collect(Collectors.toList());
  }
}
