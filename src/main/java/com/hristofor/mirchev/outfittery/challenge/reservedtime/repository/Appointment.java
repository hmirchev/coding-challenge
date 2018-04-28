package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue(value = "Appointment")
public class Appointment extends ReservedTime {

  @NotNull
  private Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
