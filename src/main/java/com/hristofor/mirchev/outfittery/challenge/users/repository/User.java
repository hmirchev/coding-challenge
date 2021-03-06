package com.hristofor.mirchev.outfittery.challenge.users.repository;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Entity
public class User implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotNull
  private LocalDate birthday;

  @NotBlank
  private String phone;

  @NotBlank
  private String address;

  @NotNull
  @Convert(converter = Jsr310JpaConverters.ZoneIdConverter.class)
  private ZoneId timeZone;

  public User() {
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public User setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public User setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public User setBirthday(LocalDate birthday) {
    this.birthday = birthday;
    return this;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public User setAddress(String address) {
    this.address = address;
    return this;
  }

  public ZoneId getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(ZoneId timeZone) {
    this.timeZone = timeZone;
  }
}
