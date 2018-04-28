package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class ReservedTime implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  private Long stylistId;

  @NotNull
  private OffsetDateTime start;

  @NotNull
  private OffsetDateTime end;

  public ReservedTime() {
  }

  public Long getId() {
    return id;
  }

  public Long getStylistId() {
    return stylistId;
  }

  public void setStylistId(Long stylistId) {
    this.stylistId = stylistId;
  }

  public OffsetDateTime getStart() {
    return start;
  }

  public void setStart(OffsetDateTime start) {
    this.start = start;
  }

  public OffsetDateTime getEnd() {
    return end;
  }

  public void setEnd(OffsetDateTime end) {
    this.end = end;
  }
}
