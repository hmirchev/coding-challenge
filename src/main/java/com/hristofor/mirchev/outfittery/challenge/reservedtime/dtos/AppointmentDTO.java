package com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class AppointmentDTO implements Serializable {

  private Long id;

  @NotNull
  private Long stylistId;

  @NotNull
  private Long userId;

  @NotNull
  private OffsetDateTime start;

  @NotNull
  private OffsetDateTime end;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private HttpStatus status;

  public AppointmentDTO() {
  }

  public Long getId() {
    return id;
  }

  public AppointmentDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getStylistId() {
    return stylistId;
  }

  public AppointmentDTO setStylistId(Long stylistId) {
    this.stylistId = stylistId;
    return this;
  }

  public Long getUserId() {
    return userId;
  }

  public AppointmentDTO setUserId(Long userId) {
    this.userId = userId;
    return this;
  }

  public OffsetDateTime getStart() {
    return start;
  }

  public AppointmentDTO setStart(OffsetDateTime start) {
    this.start = start;
    return this;
  }

  public OffsetDateTime getEnd() {
    return end;
  }

  public AppointmentDTO setEnd(OffsetDateTime end) {
    this.end = end;
    return this;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public AppointmentDTO setStatus(HttpStatus status) {
    this.status = status;
    return this;
  }
}
