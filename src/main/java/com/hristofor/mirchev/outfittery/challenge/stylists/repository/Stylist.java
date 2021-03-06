package com.hristofor.mirchev.outfittery.challenge.stylists.repository;


import java.io.Serializable;
import java.time.ZoneId;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Entity
public class Stylist implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private StylistStatus status;

  @NotNull
  @Convert(converter = Jsr310JpaConverters.ZoneIdConverter.class)
  private ZoneId timeZone;

  public Stylist() {
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public Stylist setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public Stylist setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public StylistStatus getStatus() {
    return status;
  }

  public void setStatus(StylistStatus status) {
    this.status = status;
  }

  public ZoneId getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(ZoneId timeZone) {
    this.timeZone = timeZone;
  }
}
