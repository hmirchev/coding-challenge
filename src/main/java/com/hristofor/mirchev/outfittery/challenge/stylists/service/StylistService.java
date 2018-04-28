package com.hristofor.mirchev.outfittery.challenge.stylists.service;

import com.hristofor.mirchev.outfittery.challenge.stylists.exceptions.InvalidStylistStatusTransitionException;
import com.hristofor.mirchev.outfittery.challenge.stylists.exceptions.StylistNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.Stylist;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import java.util.List;
import org.springframework.lang.NonNull;

public interface StylistService {

  /**
   * Creates a new {@link Stylist}.
   *
   * @param stylist the {@link Stylist} to create.
   * @return the newly created instance of a {@link Stylist}.
   */
  @NonNull
  Stylist createStylist(@NonNull Stylist stylist);

  /**
   * Gets a list of all {@link Stylist}.
   *
   * @return a list of all {@link Stylist} or empty list, if there are none.
   */
  @NonNull
  List<Stylist> getAllStylists();

  /**
   * Gets the {@link Stylist} with the provided id.
   *
   * @param stylistId the id of the {@link Stylist} we want to get.
   * @return the {@link Stylist} with the provided id.
   * @throws StylistNotFoundException when a {@link Stylist} with the provided id does not exist.
   */
  @NonNull
  Stylist getStylistById(@NonNull Long stylistId) throws StylistNotFoundException;

  /**
   * Gets a list of {@link Stylist} with the provided ids.
   *
   * @param stylistIds a list of the ids of the {@link Stylist} we want to get.
   * @return a list of the {@link Stylist} with the provided ids.
   */
  @NonNull
  List<Stylist> getStylistsByIds(@NonNull List<Long> stylistIds);

  /**
   * Updates the {@link Stylist} with the provided id.
   *
   * <p> Note, the updated {@link Stylist} overwrites the old one.
   *
   * @param stylistId the id of the {@link Stylist} we want to update.
   * @param updatedStylist the new data for the {@link Stylist}.
   * @return the newly updated instance of the {@link Stylist}.
   * @throws StylistNotFoundException when a {@link Stylist} with the provided id does not exist.
   */
  @NonNull
  Stylist updateStylist(@NonNull Long stylistId, @NonNull Stylist updatedStylist)
      throws StylistNotFoundException;

  /**
   * Changes the {@link StylistStatus} of the {@link Stylist} with the provided id.
   *
   * @param stylistId the id of the {@link Stylist} whose {@link StylistStatus} we want to change.
   * @param updatedStylistStatus the new {@link StylistStatus}.
   * @return the newly updated instance of the {@link Stylist}.
   * @throws StylistNotFoundException when a {@link Stylist} with the provided id does not exist.
   * @throws InvalidStylistStatusTransitionException when the updated status is an invalid
   * transition from the old status
   */
  @NonNull
  Stylist changeStylistStatus(@NonNull Long stylistId, @NonNull StylistStatus updatedStylistStatus)
      throws StylistNotFoundException, InvalidStylistStatusTransitionException;

  /**
   * Deletes the {@link Stylist} with the provided id.
   *
   * @param stylistId the id of the {@link Stylist} we want to delete.
   * @throws StylistNotFoundException when a {@link Stylist} with the provided id does not exist.
   */
  void deleteStylist(@NonNull Long stylistId) throws StylistNotFoundException;
}
