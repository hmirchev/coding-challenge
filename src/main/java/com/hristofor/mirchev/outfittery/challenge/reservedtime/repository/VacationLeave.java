package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "VacationLeave")
public class VacationLeave extends ReservedTime {
  /*
   * Same comment, this could potentially be extended with additional metadata
   * (e.g. is it a paid, or an unpaid leave, ...)
   */
}
