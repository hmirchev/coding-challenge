package com.hristofor.mirchev.outfittery.challenge.users.dtos;

import com.hristofor.mirchev.outfittery.challenge.users.repository.User;
import java.util.Objects;
import org.springframework.lang.NonNull;

public class DTOtoEntityConverter {

  /**
   * Converts a {@link User} to a {@link UserDTO}.
   *
   * @param user user data, passed as {@link User}.
   * @return a {@link UserDTO}, containing the user data, passed as {@link User}.
   */
  public static UserDTO convert(@NonNull final User user) {
    Objects.requireNonNull(user, "User should not be null here.");

    return new UserDTO()
        .setId(user.getId())
        .setEmail(user.getEmail())
        .setPassword(user.getPassword())
        .setFirstName(user.getFirstName())
        .setLastName(user.getLastName())
        .setBirthday(user.getBirthday())
        .setPhone(user.getPhone())
        .setAddress(user.getAddress())
        .setTimeZone(user.getTimeZone());
  }

  /**
   * Converts a {@link UserDTO} to a {@link User}.
   *
   * <p> Note: The {@link User} entity is not persisted in any way.
   *
   * @param userDTO user data, passed as {@link UserDTO}.
   * @return a {@link User}, containing the user data, passed as {@link UserDTO}.
   */
  public static User convert(@NonNull final UserDTO userDTO) {
    Objects.requireNonNull(userDTO, "UserDTO should not be null here.");

    final User user = new User();
    user.setEmail(userDTO.getEmail());
    user.setPassword(userDTO.getPassword());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setBirthday(userDTO.getBirthday());
    user.setPhone(userDTO.getPhone());
    user.setAddress(userDTO.getAddress());
    user.setTimeZone(userDTO.getTimeZone());

    return user;
  }
}
