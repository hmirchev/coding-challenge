package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.Appointment;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.AppointmentRepository;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Override
  @NonNull
  public Appointment createAppointment(@NonNull final Appointment appointment) {
    Objects.requireNonNull(appointment, "Sick leave should not be null here.");

    log.debug("Creating a sick leave");
    return appointmentRepository.saveAndFlush(appointment);
  }

  @Override
  @NonNull
  public List<Appointment> getAllAppointments() {
    log.debug("Getting all sick leaves");
    return appointmentRepository.findAll();
  }

  @Override
  @NonNull
  public Appointment getAppointmentById(@NonNull final Long appointmentId) {
    Objects.requireNonNull(appointmentId, "AppointmentId should not be null here.");

    log.debug("Getting a sick leave with id " + appointmentId);

    return appointmentRepository.findById(appointmentId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(appointmentId));
  }

  @Override
  @NonNull
  public Appointment updateAppointment(@NonNull final Long appointmentId,
      @NonNull final Appointment updatedAppointment) {
    Objects.requireNonNull(appointmentId, "AppointmentId should not be null here.");
    Objects.requireNonNull(updatedAppointment, "The updated sick leave should not be null here.");

    Appointment appointment = appointmentRepository.findById(appointmentId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(appointmentId));

    appointment.setStylistId(updatedAppointment.getStylistId());
    appointment.setStart(updatedAppointment.getStart());
    appointment.setEnd(updatedAppointment.getEnd());

    log.debug("Updating a sick leave with id " + appointmentId);
    return appointmentRepository.saveAndFlush(appointment);
  }

  @Override
  public void deleteAppointment(@NonNull final Long appointmentId) {
    Objects.requireNonNull(appointmentId, "AppointmentId should not be null here.");

    final Appointment appointment = appointmentRepository.findById(appointmentId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(appointmentId));

    log.debug("Deleting a sick leave with id " + appointmentId);
    appointmentRepository.delete(appointment);
  }
}
