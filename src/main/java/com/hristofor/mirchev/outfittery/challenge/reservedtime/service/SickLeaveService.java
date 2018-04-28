package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.SickLeave;
import java.util.List;
import org.springframework.lang.NonNull;

public interface SickLeaveService {

  /**
   * Creates a new {@link SickLeave}.
   *
   * @param sickLeave the {@link SickLeave} to create.
   * @return the newly created instance of a {@link SickLeave}.
   */
  @NonNull
  SickLeave createSickLeave(@NonNull SickLeave sickLeave);

  /**
   * Gets a list of all {@link SickLeave}.
   *
   * @return a list of all {@link SickLeave} or empty list, if there are none.
   */
  @NonNull
  List<SickLeave> getAllSickLeaves();

  /**
   * Gets the {@link SickLeave} with the provided id.
   *
   * @param sickLeaveId the id of the {@link SickLeave} we want to get.
   * @return the {@link SickLeave} with the provided id.
   * @throws ReservedTimeNotFoundException when a {@link SickLeave} with the provided id does not
   * exist.
   */
  @NonNull
  SickLeave getSickLeaveById(@NonNull Long sickLeaveId) throws ReservedTimeNotFoundException;

  /**
   * Updates the {@link SickLeave} with the provided id.
   *
   * <p> Note, the updated {@link SickLeave} overwrites the old one.
   *
   * @param sickLeaveId the id of the {@link SickLeave} we want to update.
   * @param updatedSickLeave the new data for the {@link SickLeave}.
   * @return the newly updated instance of the {@link SickLeave}.
   * @throws ReservedTimeNotFoundException when a {@link SickLeave} with the provided id does not
   * exist.
   */
  @NonNull
  SickLeave updateSickLeave(@NonNull Long sickLeaveId, @NonNull SickLeave updatedSickLeave)
      throws ReservedTimeNotFoundException;

  /**
   * Deletes the {@link SickLeave} with the provided id.
   *
   * @param sickLeaveId the id of the {@link SickLeave} we want to delete.
   * @throws ReservedTimeNotFoundException when a {@link SickLeave} with the provided id does not
   * exist.
   */
  void deleteSickLeave(@NonNull Long sickLeaveId) throws ReservedTimeNotFoundException;
}
