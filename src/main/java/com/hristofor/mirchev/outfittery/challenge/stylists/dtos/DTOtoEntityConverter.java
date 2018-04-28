package com.hristofor.mirchev.outfittery.challenge.stylists.dtos;

import com.hristofor.mirchev.outfittery.challenge.stylists.repository.Stylist;
import java.util.Objects;
import org.springframework.lang.NonNull;

public class DTOtoEntityConverter {

  /**
   * Converts a {@link Stylist} to a {@link StylistDTO}.
   *
   * @param stylist stylist data, passed as {@link Stylist}.
   * @return a {@link StylistDTO}, containing the stylist data, passed as {@link Stylist}.
   */
  public static StylistDTO convert(@NonNull final Stylist stylist) {
    Objects.requireNonNull(stylist, "Stylist should not be null here.");

    return new StylistDTO()
        .setId(stylist.getId())
        .setFirstName(stylist.getFirstName())
        .setLastName(stylist.getLastName())
        .setStatus(stylist.getStatus())
        .setTimeZone(stylist.getTimeZone());
  }

  /**
   * Converts a {@link StylistDTO} to a {@link Stylist}.
   *
   * <p> Note: The {@link Stylist} entity is not persisted in any way.
   *
   * @param stylistDTO stylist data, passed as {@link StylistDTO}.
   * @return a {@link Stylist}, containing the stylist data, passed as {@link StylistDTO}.
   */
  public static Stylist convert(@NonNull final StylistDTO stylistDTO) {
    Objects.requireNonNull(stylistDTO, "StylistDTO should not be null here.");

    final Stylist stylist = new Stylist();
    stylist.setFirstName(stylistDTO.getFirstName());
    stylist.setLastName(stylistDTO.getLastName());
    stylist.setStatus(stylistDTO.getStatus());
    stylist.setTimeZone(stylistDTO.getTimeZone());

    return stylist;
  }
}
