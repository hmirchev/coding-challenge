package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SickLeave")
public class SickLeave extends ReservedTime {
  /*
   * Can imagine that this could later be expanded with additional metadata
   * (e.g. upload of doctor's note, ...)
   */

}
