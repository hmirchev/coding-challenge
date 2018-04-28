package com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos;

import java.io.Serializable;
import java.util.List;

public class AppointmentBatchDTO implements Serializable {

  private List<AppointmentDTO> appointments;

  public AppointmentBatchDTO() {
  }

  public List<AppointmentDTO> getAppointments() {
    return appointments;
  }

  public AppointmentBatchDTO setAppointments(List<AppointmentDTO> appointments) {
    this.appointments = appointments;
    return this;
  }
}
