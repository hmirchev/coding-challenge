package com.hristofor.mirchev.outfittery.challenge.reservedtime.controller;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.AppointmentBatchDTO;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.AppointmentDTO;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos.DTOtoEntityConverter;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.InvalidStylistStatusException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeOverlapsException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.Appointment;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.AppointmentService;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.ReservedTimeService;
import com.hristofor.mirchev.outfittery.challenge.stylists.controller.StylistController;
import com.hristofor.mirchev.outfittery.challenge.stylists.dtos.StylistDTO;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@Api(description = "Operations regarding the appointments with clients for a phone call.")
@RestController
@RequestMapping("/v1")
public class AppointmentController {

  @Autowired
  private AppointmentService appointmentService;

  @Autowired
  private StylistController stylistController;

  @Autowired
  private ReservedTimeService reservedTimeService;

  @ApiOperation(value = "Creates a new appointment. Note, that we validate the appointment against constraints.", response = AppointmentDTO.class)
  @ApiResponse(code = 400, message = "The appointment with a client is invalid against the constraints.")
  @PostMapping(value = "/appointments", produces = "application/json")
  public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
    validateAppointment(appointmentDTO);

    final Appointment transientAppointment = DTOtoEntityConverter.convert(appointmentDTO);
    final Appointment persistentAppointment = appointmentService
        .createAppointment(transientAppointment);

    // setting the location header to point to the created user
    final URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}").buildAndExpand(persistentAppointment.getId()).toUri();

    return ResponseEntity.created(location)
        .body(DTOtoEntityConverter.convert(persistentAppointment));
  }

  @ApiOperation(value = "Creates many new appointments in a batch. Note, that we validate the appointments against constraints.", response = AppointmentBatchDTO.class)
  @PostMapping(value = "/appointments/batch", produces = "application/json")
  public ResponseEntity<?> createAppointmentBatch(
      @Valid @RequestBody AppointmentBatchDTO appointmentBatchDTO) {
    Objects.requireNonNull(appointmentBatchDTO.getAppointments(),
        "Appointments in AppointmentBatchDTO should not be null here.");

    final List<AppointmentDTO> resultingAppointments = new ArrayList<>();

    for (final AppointmentDTO appointmentDTO : appointmentBatchDTO.getAppointments()) {

      try {
        validateAppointment(appointmentDTO);
      } catch (final InvalidStylistStatusException | ReservedTimeOverlapsException ex) {
        appointmentDTO.setStatus(HttpStatus.BAD_REQUEST);
        resultingAppointments.add(appointmentDTO);
        continue;
      }

      final Appointment transientAppointment = DTOtoEntityConverter.convert(appointmentDTO);
      final Appointment persistentAppointment = appointmentService
          .createAppointment(transientAppointment);
      final AppointmentDTO persistentAppointmentDTO = DTOtoEntityConverter
          .convert(persistentAppointment);

      persistentAppointmentDTO.setStatus(HttpStatus.CREATED);
      resultingAppointments.add(persistentAppointmentDTO);
    }

    final AppointmentBatchDTO resultingBatchDTO = new AppointmentBatchDTO()
        .setAppointments(resultingAppointments);
    return ResponseEntity.ok().body(resultingBatchDTO);
  }

  @ApiOperation(value = "Gets a list of all appointments.", response = List.class)
  @GetMapping(value = "/appointments", produces = "application/json")
  public List<AppointmentDTO> getAllAppointments() {
    return appointmentService.getAllAppointments().stream().map(DTOtoEntityConverter::convert)
        .collect(Collectors.toList());
  }

  @ApiOperation(value = "Gets the appointment with the provided id.", response = AppointmentDTO.class)
  @ApiResponse(code = 404, message = "The appointment with the provided id could not be found.")
  @GetMapping(value = "/appointments/{id}", produces = "application/json")
  public AppointmentDTO getAppointmentById(@PathVariable(value = "id") Long appointmentId) {

    final Appointment stylist = appointmentService.getAppointmentById(appointmentId);
    return DTOtoEntityConverter.convert(stylist);
  }

  @ApiOperation(value = "Updates the appointment with the provided id. Note, that we validate the appointment against constraints.", response = AppointmentDTO.class)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "The new appointment with a client is invalid against the constraints."),
      @ApiResponse(code = 404, message = "The appointment with the provided id could not be found.")
  })
  @PutMapping(value = "/appointments/{id}", produces = "application/json")
  public AppointmentDTO updateAppointment(@PathVariable(value = "id") Long appointmentId,
      @Valid @RequestBody AppointmentDTO updatedAppointment) {
    validateAppointment(updatedAppointment);

    final Appointment transientUpdatedAppointment = DTOtoEntityConverter
        .convert(updatedAppointment);
    final Appointment persistentUpdatedAppointment = appointmentService
        .updateAppointment(appointmentId, transientUpdatedAppointment);
    return DTOtoEntityConverter.convert(persistentUpdatedAppointment);
  }

  @ApiOperation(value = "Deletes the appointment with the provided id.")
  @ApiResponse(code = 404, message = "The appointment with the provided id could not be found.")
  @DeleteMapping(value = "/appointments/{id}", produces = "application/json")
  public ResponseEntity<?> deleteAppointment(@PathVariable(value = "id") Long appointmentId) {
    appointmentService.deleteAppointment(appointmentId);

    return ResponseEntity.ok().build();
  }

  /**
   * This method validates that the appointment we want to create/update does not violate these
   * constraints: <br/>
   * <p>
   * 1) the stylist for whom we are creating the appointment, should have a {@link
   * StylistStatus#READY_TO_STYLE} status
   * <p>
   * 2) the stylist for whom we are creating the appointment, should not have another reserved time
   * that overlaps
   *
   * @param appointmentDTO the appointment we want to create/update.
   */
  private void validateAppointment(@NonNull final AppointmentDTO appointmentDTO)
      throws InvalidStylistStatusException, ReservedTimeOverlapsException {
    Objects.requireNonNull(appointmentDTO, "AppointmentDTO should not be null here");

    final Long stylistId = appointmentDTO.getStylistId();
    Objects.requireNonNull(stylistId, "StylistId should not be null here");

    // simulating the call to the stylist microservice here
    final StylistDTO stylist = stylistController.getStylistById(stylistId);
    if (stylist.getStatus() != StylistStatus.READY_TO_STYLE) {
      throw new InvalidStylistStatusException(stylistId);
    }

    final OffsetDateTime start = appointmentDTO.getStart();
    Objects.requireNonNull(start, "Start OffsetDateTime should not be null here.");

    final OffsetDateTime end = appointmentDTO.getEnd();
    Objects.requireNonNull(end, "End OffsetDateTime should not be null here.");

    final List<ReservedTime> timesThatOverlap = reservedTimeService
        .getReservedTimesOfStylistThatOverlapWith(stylistId, start, end);
    if (!CollectionUtils.isEmpty(timesThatOverlap)) {
      throw new ReservedTimeOverlapsException(stylistId);
    }
  }
}
