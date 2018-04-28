package com.hristofor.mirchev.outfittery.challenge.stylists.dtos;

import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import java.io.Serializable;
import java.time.ZoneId;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StylistDTO implements Serializable {

  private Long id;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotNull
  private StylistStatus status;

  @NotNull
  private ZoneId timeZone;

  public StylistDTO() {
  }

  public Long getId() {
    return id;
  }

  public StylistDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public StylistDTO setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public StylistDTO setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public StylistStatus getStatus() {
    return status;
  }

  public StylistDTO setStatus(StylistStatus status) {
    this.status = status;
    return this;
  }

  public ZoneId getTimeZone() {
    return timeZone;
  }

  public StylistDTO setTimeZone(ZoneId timeZone) {
    this.timeZone = timeZone;
    return this;
  }
}
