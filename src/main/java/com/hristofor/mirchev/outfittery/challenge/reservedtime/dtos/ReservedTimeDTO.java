package com.hristofor.mirchev.outfittery.challenge.reservedtime.dtos;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;

public class ReservedTimeDTO implements Serializable {

  private Long id;

  @NotNull
  private Long stylistId;

  @NotNull
  private OffsetDateTime start;

  @NotNull
  private OffsetDateTime end;

  public ReservedTimeDTO() {
  }

  public Long getId() {
    return id;
  }

  public ReservedTimeDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getStylistId() {
    return stylistId;
  }

  public ReservedTimeDTO setStylistId(Long stylistId) {
    this.stylistId = stylistId;
    return this;
  }

  public OffsetDateTime getStart() {
    return start;
  }

  public ReservedTimeDTO setStart(OffsetDateTime start) {
    this.start = start;
    return this;
  }

  public OffsetDateTime getEnd() {
    return end;
  }

  public ReservedTimeDTO setEnd(OffsetDateTime end) {
    this.end = end;
    return this;
  }
}
