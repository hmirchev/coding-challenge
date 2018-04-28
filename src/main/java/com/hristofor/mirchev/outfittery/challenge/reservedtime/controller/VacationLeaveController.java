package com.hristofor.mirchev.outfittery.challenge.reservedtime.controller;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.DTOtoEntityConverter;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.VacationLeaveDTO;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.InvalidStylistStatusException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeOverlapsException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.VacationLeave;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.ReservedTimeService;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.VacationLeaveService;
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

@Api(description = "Operations regarding the vacation leaves.")
@RestController
@RequestMapping("/v1")
public class VacationLeaveController {

  @Autowired
  private VacationLeaveService vacationLeaveService;

  @Autowired
  private StylistController stylistController;

  @Autowired
  private ReservedTimeService reservedTimeService;

  @ApiOperation(value = "Creates a new vacation leave. Note, that we validate the vacation leave against constraints.", response = VacationLeaveDTO.class)
  @ApiResponse(code = 400, message = "The vacation leave is invalid against the constraints.")
  @PostMapping(value = "/vacationLeaves", produces = "application/json")
  public ResponseEntity<?> createVacationLeave(
      @Valid @RequestBody VacationLeaveDTO vacationLeaveDTO) {
    validateVacationLeave(vacationLeaveDTO);

    final VacationLeave transientVacationLeave = DTOtoEntityConverter.convert(vacationLeaveDTO);
    final VacationLeave persistentVacationLeave = vacationLeaveService
        .createVacationLeave(transientVacationLeave);

    // setting the location header to point to the created user
    final URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}").buildAndExpand(persistentVacationLeave.getId()).toUri();

    return ResponseEntity.created(location)
        .body(DTOtoEntityConverter.convert(persistentVacationLeave));
  }

  @ApiOperation(value = "Gets a list of all vacation leaves.", response = List.class)
  @GetMapping(value = "/vacationLeaves", produces = "application/json")
  public List<VacationLeaveDTO> getAllVacationLeaves() {
    return vacationLeaveService.getAllVacationLeaves().stream().map(DTOtoEntityConverter::convert)
        .collect(Collectors.toList());
  }

  @ApiOperation(value = "Gets the vacation leave with the provided id.", response = VacationLeaveDTO.class)
  @ApiResponse(code = 404, message = "The vacation leave with the provided id could not be found.")
  @GetMapping(value = "/vacationLeaves/{id}", produces = "application/json")
  public VacationLeaveDTO getVacationLeaveById(@PathVariable(value = "id") Long vacationLeaveId) {

    final VacationLeave stylist = vacationLeaveService.getVacationLeaveById(vacationLeaveId);
    return DTOtoEntityConverter.convert(stylist);
  }

  @ApiOperation(value = "Updates the vacation leave with the provided id. Note, that we validate the vacation leave against constraints.", response = VacationLeaveDTO.class)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "The new vacation leave is invalid against the constraints."),
      @ApiResponse(code = 404, message = "The vacation leave with the provided id could not be found.")
  })
  @PutMapping(value = "/vacationLeaves/{id}", produces = "application/json")
  public VacationLeaveDTO updateVacationLeave(@PathVariable(value = "id") Long vacationLeaveId,
      @Valid @RequestBody VacationLeaveDTO updatedVacationLeave) {
    validateVacationLeave(updatedVacationLeave);

    final VacationLeave transientUpdatedVacationLeave = DTOtoEntityConverter
        .convert(updatedVacationLeave);
    final VacationLeave persistentUpdatedVacationLeave = vacationLeaveService
        .updateVacationLeave(vacationLeaveId, transientUpdatedVacationLeave);
    return DTOtoEntityConverter.convert(persistentUpdatedVacationLeave);
  }

  @ApiOperation(value = "Deletes the vacation leave with the provided id.")
  @ApiResponse(code = 404, message = "The vacation leave with the provided id could not be found.")
  @DeleteMapping(value = "/vacationLeaves/{id}", produces = "application/json")
  public ResponseEntity<?> deleteVacationLeave(@PathVariable(value = "id") Long vacationLeaveId) {
    vacationLeaveService.deleteVacationLeave(vacationLeaveId);

    return ResponseEntity.ok().build();
  }

  /**
   * This method validates that the vacation leave we want to create/update does not violate these
   * constraints: <br/>
   * <p>
   * 1) the stylist for whom we are creating the vacation leave, should not be {@link
   * StylistStatus#OFFBOARDED} (the state machine loses some logic when creating vacation leaves,
   * because, I assume, a {@link StylistStatus#ROOKIE} and {@link StylistStatus#SICK} stylist can
   * also take a vacation leave)
   * <p>
   * 2) the stylist for whom we are creating the vacation leave, should not have another reserved
   * time that overlaps
   *
   * @param vacationLeaveDTO the vacation leave we want to create/update.
   */
  private void validateVacationLeave(@NonNull final VacationLeaveDTO vacationLeaveDTO) {
    Objects.requireNonNull(vacationLeaveDTO, "VacationLeaveDTO should not be null here");

    final Long stylistId = vacationLeaveDTO.getStylistId();
    Objects.requireNonNull(stylistId, "StylistId should not be null here");

    // simulating the call to the stylist microservice here
    final StylistDTO stylist = stylistController.getStylistById(stylistId);
    if (stylist.getStatus() == StylistStatus.OFFBOARDED) {
      throw new InvalidStylistStatusException(stylistId);
    }

    final OffsetDateTime start = vacationLeaveDTO.getStart();
    Objects.requireNonNull(start, "Start OffsetDateTime should not be null here.");

    final OffsetDateTime end = vacationLeaveDTO.getEnd();
    Objects.requireNonNull(end, "End OffsetDateTime should not be null here.");

    final List<ReservedTime> timesThatOverlap = reservedTimeService
        .getReservedTimesOfStylistThatOverlapWith(stylistId, start, end);
    if (!CollectionUtils.isEmpty(timesThatOverlap)) {
      throw new ReservedTimeOverlapsException(stylistId);
    }
  }
}
