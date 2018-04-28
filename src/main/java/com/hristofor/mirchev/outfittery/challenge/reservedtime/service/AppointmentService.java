package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.Appointment;
import java.util.List;
import org.springframework.lang.NonNull;

public interface AppointmentService {

  /**
   * Creates a new {@link Appointment}.
   *
   * @param appointment the {@link Appointment} to create.
   * @return the newly created instance of a {@link Appointment}.
   */
  @NonNull
  Appointment createAppointment(@NonNull Appointment appointment);

  /**
   * Gets a list of all {@link Appointment}.
   *
   * @return a list of all {@link Appointment} or empty list, if there are none.
   */
  @NonNull
  List<Appointment> getAllAppointments();

  /**
   * Gets the {@link Appointment} with the provided id.
   *
   * @param appointmentId the id of the {@link Appointment} we want to get.
   * @return the {@link Appointment} with the provided id.
   * @throws ReservedTimeNotFoundException when a {@link Appointment} with the provided id does not
   * exist.
   */
  @NonNull
  Appointment getAppointmentById(@NonNull Long appointmentId) throws ReservedTimeNotFoundException;

  /**
   * Updates the {@link Appointment} with the provided id.
   *
   * <p> Note, the updated {@link Appointment} overwrites the old one.
   *
   * @param appointmentId the id of the {@link Appointment} we want to update.
   * @param updatedAppointment the new data for the {@link Appointment}.
   * @return the newly updated instance of the {@link Appointment}.
   * @throws ReservedTimeNotFoundException when a {@link Appointment} with the provided id does not
   * exist.
   */
  @NonNull
  Appointment updateAppointment(@NonNull Long appointmentId,
      @NonNull Appointment updatedAppointment) throws ReservedTimeNotFoundException;

  /**
   * Deletes the {@link Appointment} with the provided id.
   *
   * @param appointmentId the id of the {@link Appointment} we want to delete.
   * @throws ReservedTimeNotFoundException when a {@link Appointment} with the provided id does not
   * exist.
   */
  void deleteAppointment(@NonNull Long appointmentId) throws ReservedTimeNotFoundException;
}
