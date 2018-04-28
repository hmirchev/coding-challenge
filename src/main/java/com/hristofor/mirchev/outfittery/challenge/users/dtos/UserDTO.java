package com.hristofor.mirchev.outfittery.challenge.users.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDTO implements Serializable {

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
  private ZoneId timeZone;

  public UserDTO() {
  }

  public Long getId() {
    return id;
  }

  public UserDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserDTO setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserDTO setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserDTO setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserDTO setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public UserDTO setBirthday(LocalDate birthday) {
    this.birthday = birthday;
    return this;
  }

  public String getPhone() {
    return phone;
  }

  public UserDTO setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public String getAddress() {
    return address;
  }

  public UserDTO setAddress(String address) {
    this.address = address;
    return this;
  }

  public ZoneId getTimeZone() {
    return timeZone;
  }

  public UserDTO setTimeZone(ZoneId timeZone) {
    this.timeZone = timeZone;
    return this;
  }
}
