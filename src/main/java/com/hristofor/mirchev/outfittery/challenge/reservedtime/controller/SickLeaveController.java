package com.hristofor.mirchev.outfittery.challenge.reservedtime.controller;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.DTOtoEntityConverter;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.SickLeaveDTO;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.InvalidStylistStatusException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeOverlapsException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.SickLeave;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.ReservedTimeService;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.SickLeaveService;
import com.hristofor.mirchev.outfittery.challenge.stylists.controller.StylistController;
import com.hristofor.mirchev.outfittery.challenge.stylists.dtos.StylistDTO;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(description = "Operations regarding the sick leaves.")
@RestController
@RequestMapping("/v1")
public class SickLeaveController {

  @Autowired
  private SickLeaveService sickLeaveService;

  @Autowired
  private StylistController stylistController;

  @Autowired
  private ReservedTimeService reservedTimeService;

  @ApiOperation(value = "Creates a new sick leave. Note, that we validate the sick leave against constraints.", response = SickLeaveDTO.class)
  @ApiResponse(code = 400, message = "The sick leave we want to create is invalid against the constraints.")
  @PostMapping(value = "/sickLeaves", produces = "application/json")
  public ResponseEntity<?> createSickLeave(@Valid @RequestBody SickLeaveDTO sickLeaveDTO) {
    validateSickLeave(sickLeaveDTO);

    final SickLeave transientSickLeave = DTOtoEntityConverter.convert(sickLeaveDTO);
    final SickLeave persistentSickLeave = sickLeaveService.createSickLeave(transientSickLeave);

    // setting the location header to point to the created user
    final URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}").buildAndExpand(persistentSickLeave.getId()).toUri();

    return ResponseEntity.created(location).body(DTOtoEntityConverter.convert(persistentSickLeave));
  }

  @ApiOperation(value = "Gets a list of all sick leaves.", response = List.class)
  @GetMapping(value = "/sickLeaves", produces = "application/json")
  public List<SickLeaveDTO> getAllSickLeaves() {
    return sickLeaveService.getAllSickLeaves().stream().map(DTOtoEntityConverter::convert)
        .collect(Collectors.toList());
  }

  @ApiOperation(value = "Gets the sick leave with the provided id.", response = SickLeaveDTO.class)
  @ApiResponse(code = 404, message = "The sick leave with the provided id could not be found.")
  @GetMapping(value = "/sickLeaves/{id}", produces = "application/json")
  public SickLeaveDTO getSickLeaveById(@PathVariable(value = "id") Long sickLeaveId) {

    final SickLeave stylist = sickLeaveService.getSickLeaveById(sickLeaveId);
    return DTOtoEntityConverter.convert(stylist);
  }

  @ApiOperation(value = "Updates the sick leave with the provided id. Note, that we validate the sick leave against constraints.", response = SickLeaveDTO.class)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "The new sick leave is invalid against the constraints."),
      @ApiResponse(code = 404, message = "The sick leave with the provided id could not be found.")
  })
  @PutMapping(value = "/sickLeaves/{id}", produces = "application/json")
  public SickLeaveDTO updateSickLeave(@PathVariable(value = "id") Long sickLeaveId,
      @Valid @RequestBody SickLeaveDTO updatedSickLeave) {
    validateSickLeave(updatedSickLeave);

    final SickLeave transientUpdatedSickLeave = DTOtoEntityConverter.convert(updatedSickLeave);
    final SickLeave persistentUpdatedSickLeave = sickLeaveService
        .updateSickLeave(sickLeaveId, transientUpdatedSickLeave);
    return DTOtoEntityConverter.convert(persistentUpdatedSickLeave);
  }

  @ApiOperation(value = "Deletes the sick leave with the provided id.")
  @ApiResponse(code = 404, message = "The sick leave with the provided id could not be found.")
  @DeleteMapping(value = "/sickLeaves/{id}", produces = "application/json")
  public ResponseEntity<?> deleteSickLeave(@PathVariable(value = "id") Long sickLeaveId) {
    sickLeaveService.deleteSickLeave(sickLeaveId);

    return ResponseEntity.ok().build();
  }

  /**
   * This method validates that the sick leave we want to create/update does not violate these
   * constraints: <br/>
   * <p>
   * 1) the stylist for whom we are creating the sick leave, should not be {@link
   * StylistStatus#OFFBOARDED} (the state machine loses some logic when creating sick leaves,
   * because, I assume, a {@link StylistStatus#ROOKIE} and {@link StylistStatus#ON_HOLIDAYS} stylist
   * can also take a sick leave)
   * <p>
   * 2) the stylist for whom we are creating the sick leave, should not have another reserved time
   * that overlaps
   *
   * @param sickLeaveDTO the sick leave we want to create/update.
   */
  private void validateSickLeave(@NonNull final SickLeaveDTO sickLeaveDTO) {
    Objects.requireNonNull(sickLeaveDTO, "SickLeaveDTO should not be null here");

    final Long stylistId = sickLeaveDTO.getStylistId();
    Objects.requireNonNull(stylistId, "StylistId should not be null here");

    // simulating the call to the stylist microservice here
    final StylistDTO stylist = stylistController.getStylistById(stylistId);
    if (stylist.getStatus() == StylistStatus.OFFBOARDED) {
      throw new InvalidStylistStatusException(stylistId);
    }

    final OffsetDateTime start = sickLeaveDTO.getStart();
    Objects.requireNonNull(start, "Start OffsetDateTime should not be null here.");

    final OffsetDateTime end = sickLeaveDTO.getEnd();
    Objects.requireNonNull(end, "End OffsetDateTime should not be null here.");

    final List<ReservedTime> timesThatOverlap = reservedTimeService
        .getReservedTimesOfStylistThatOverlapWith(stylistId, start, end);
    if (!CollectionUtils.isEmpty(timesThatOverlap)) {
      throw new ReservedTimeOverlapsException(stylistId);
    }
  }
}
