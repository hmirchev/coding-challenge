package com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.Appointment;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.SickLeave;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.VacationLeave;
import java.util.Objects;
import org.springframework.lang.NonNull;

public class DTOtoEntityConverter {

  /**
   * Converts a {@link ReservedTime} to a {@link ReservedTimeDTO}.
   *
   * @param reservedTime reserved time data, passed as {@link ReservedTime}.
   * @return a {@link ReservedTimeDTO}, containing the reserved time data, passed as {@link
   * ReservedTime}.
   */
  public static ReservedTimeDTO convert(@NonNull final ReservedTime reservedTime) {
    Objects.requireNonNull(reservedTime, "Reserved time should not be null here.");

    return new ReservedTimeDTO()
        .setId(reservedTime.getId())
        .setStylistId(reservedTime.getStylistId())
        .setStart(reservedTime.getStart())
        .setEnd(reservedTime.getEnd());
  }

  /**
   * Converts a {@link SickLeave} to a {@link SickLeaveDTO}.
   *
   * @param sickLeave sick leave data, passed as {@link SickLeave}.
   * @return a {@link SickLeaveDTO}, containing the sick leave data, passed as {@link SickLeave}.
   */
  public static SickLeaveDTO convert(@NonNull final SickLeave sickLeave) {
    Objects.requireNonNull(sickLeave, "Sick leave should not be null here.");

    return new SickLeaveDTO()
        .setId(sickLeave.getId())
        .setStylistId(sickLeave.getStylistId())
        .setStart(sickLeave.getStart())
        .setEnd(sickLeave.getEnd());
  }

  /**
   * Converts a {@link SickLeaveDTO} to a {@link SickLeave}.
   *
   * <p> Note: The {@link SickLeave} entity is not persisted in any way.
   *
   * @param sickLeaveDTO sick leave data, passed as {@link SickLeaveDTO}.
   * @return a {@link SickLeave}, containing the sick leave data, passed as {@link SickLeaveDTO}.
   */
  public static SickLeave convert(@NonNull final SickLeaveDTO sickLeaveDTO) {
    Objects.requireNonNull(sickLeaveDTO, "SickLeaveDTO should not be null here.");

    final SickLeave sickLeave = new SickLeave();
    sickLeave.setStylistId(sickLeaveDTO.getStylistId());
    sickLeave.setStart(sickLeaveDTO.getStart());
    sickLeave.setEnd(sickLeaveDTO.getEnd());

    return sickLeave;
  }

  /**
   * Converts a {@link VacationLeave} to a {@link VacationLeaveDTO}.
   *
   * @param vacationLeave vacation leave data, passed as {@link VacationLeave}.
   * @return a {@link VacationLeaveDTO}, containing the vacation leave data, passed as {@link
   * VacationLeave}.
   */
  public static VacationLeaveDTO convert(@NonNull final VacationLeave vacationLeave) {
    Objects.requireNonNull(vacationLeave, "Vacation leave should not be null here.");

    return new VacationLeaveDTO()
        .setId(vacationLeave.getId())
        .setStylistId(vacationLeave.getStylistId())
        .setStart(vacationLeave.getStart())
        .setEnd(vacationLeave.getEnd());
  }

  /**
   * Converts a {@link VacationLeaveDTO} to a {@link VacationLeave}.
   *
   * <p> Note: The {@link VacationLeave} entity is not persisted in any way.
   *
   * @param vacationLeaveDTO vaction leave data, passed as {@link VacationLeaveDTO}.
   * @return a {@link VacationLeave}, containing the vacation leave data, passed as {@link
   * VacationLeaveDTO}.
   */
  public static VacationLeave convert(@NonNull final VacationLeaveDTO vacationLeaveDTO) {
    Objects.requireNonNull(vacationLeaveDTO, "VacationLeaveDTO should not be null here.");

    final VacationLeave vacationLeave = new VacationLeave();
    vacationLeave.setStylistId(vacationLeaveDTO.getStylistId());
    vacationLeave.setStart(vacationLeaveDTO.getStart());
    vacationLeave.setEnd(vacationLeaveDTO.getEnd());

    return vacationLeave;
  }

  /**
   * Converts a {@link Appointment} to a {@link AppointmentDTO}.
   *
   * @param appointment Appointment data, passed as {@link Appointment}.
   * @return a {@link AppointmentDTO}, containing the Appointment data, passed as {@link
   * Appointment}.
   */
  public static AppointmentDTO convert(@NonNull final Appointment appointment) {
    Objects.requireNonNull(appointment, "Appointment should not be null here.");

    return new AppointmentDTO()
        .setId(appointment.getId())
        .setStylistId(appointment.getStylistId())
        .setUserId(appointment.getUserId())
        .setStart(appointment.getStart())
        .setEnd(appointment.getEnd());
  }

  /**
   * Converts a {@link AppointmentDTO} to a {@link Appointment}.
   *
   * <p> Note: The {@link Appointment} entity is not persisted in any way.
   *
   * @param appointmentDTO vaction leave data, passed as {@link AppointmentDTO}.
   * @return a {@link Appointment}, containing the Appointment data, passed as {@link
   * AppointmentDTO}.
   */
  public static Appointment convert(@NonNull final AppointmentDTO appointmentDTO) {
    Objects.requireNonNull(appointmentDTO, "AppointmentDTO should not be null here.");

    final Appointment appointment = new Appointment();
    appointment.setStylistId(appointmentDTO.getStylistId());
    appointment.setUserId(appointmentDTO.getUserId());
    appointment.setStart(appointmentDTO.getStart());
    appointment.setEnd(appointmentDTO.getEnd());

    return appointment;
  }
}
