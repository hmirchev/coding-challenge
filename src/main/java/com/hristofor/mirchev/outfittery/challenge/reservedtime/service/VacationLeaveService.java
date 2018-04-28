package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.VacationLeave;
import java.util.List;
import org.springframework.lang.NonNull;

public interface VacationLeaveService {

  /**
   * Creates a new {@link VacationLeave}.
   *
   * @param vacationLeave the {@link VacationLeave} to create.
   * @return the newly created instance of a {@link VacationLeave}.
   */
  @NonNull
  VacationLeave createVacationLeave(@NonNull VacationLeave vacationLeave);

  /**
   * Gets a list of all {@link VacationLeave}.
   *
   * @return a list of all {@link VacationLeave} or empty list, if there are none.
   */
  @NonNull
  List<VacationLeave> getAllVacationLeaves();

  /**
   * Gets the {@link VacationLeave} with the provided id.
   *
   * @param vactionLeaveId the id of the {@link VacationLeave} we want to get.
   * @return the {@link VacationLeave} with the provided id.
   * @throws ReservedTimeNotFoundException when a {@link VacationLeave} with the provided id does
   * not exist.
   */
  @NonNull
  VacationLeave getVacationLeaveById(@NonNull Long vactionLeaveId)
      throws ReservedTimeNotFoundException;

  /**
   * Updates the {@link VacationLeave} with the provided id.
   *
   * <p> Note, the updated {@link VacationLeave} overwrites the old one.
   *
   * @param vactionLeaveId the id of the {@link VacationLeave} we want to update.
   * @param updatedVactionLeave the new data for the {@link VacationLeave}.
   * @return the newly updated instance of the {@link VacationLeave}.
   * @throws ReservedTimeNotFoundException when a {@link VacationLeave} with the provided id does
   * not exist.
   */
  @NonNull
  VacationLeave updateVacationLeave(@NonNull Long vactionLeaveId,
      @NonNull VacationLeave updatedVactionLeave) throws ReservedTimeNotFoundException;

  /**
   * Deletes the {@link VacationLeave} with the provided id.
   *
   * @param vactionLeaveId the id of the {@link VacationLeave} we want to delete.
   * @throws ReservedTimeNotFoundException when a {@link VacationLeave} with the provided id does
   * not exist.
   */
  void deleteVacationLeave(@NonNull Long vactionLeaveId) throws ReservedTimeNotFoundException;
}
