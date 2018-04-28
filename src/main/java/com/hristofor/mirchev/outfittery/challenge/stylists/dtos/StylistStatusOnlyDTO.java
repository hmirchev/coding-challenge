package com.hristofor.mirchev.outfittery.challenge.stylists.dtos;

import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class StylistStatusOnlyDTO implements Serializable {

  @NotNull
  private StylistStatus status;

  public StylistStatusOnlyDTO() {
  }

  public StylistStatus getStatus() {
    return status;
  }

  public StylistStatusOnlyDTO setStatus(StylistStatus status) {
    this.status = status;
    return this;
  }
}
