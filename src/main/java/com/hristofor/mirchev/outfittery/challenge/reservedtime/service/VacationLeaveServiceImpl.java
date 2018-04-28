package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.VacationLeave;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.VacationLeaveRepository;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class VacationLeaveServiceImpl implements VacationLeaveService {

  private static final Logger log = LoggerFactory.getLogger(VacationLeaveServiceImpl.class);

  @Autowired
  private VacationLeaveRepository vacationLeaveRepository;

  @Override
  @NonNull
  public VacationLeave createVacationLeave(@NonNull final VacationLeave vacationLeave) {
    Objects.requireNonNull(vacationLeave, "Vacation leave should not be null here.");

    log.debug("Creating a vacation leave");
    return vacationLeaveRepository.saveAndFlush(vacationLeave);
  }

  @Override
  @NonNull
  public List<VacationLeave> getAllVacationLeaves() {
    log.debug("Getting all vacation leaves");
    return vacationLeaveRepository.findAll();
  }

  @Override
  @NonNull
  public VacationLeave getVacationLeaveById(@NonNull final Long vacationLeaveId) {
    Objects.requireNonNull(vacationLeaveId, "VacationLeaveId should not be null here.");

    log.debug("Getting a vacation leave with id " + vacationLeaveId);

    return vacationLeaveRepository.findById(vacationLeaveId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(vacationLeaveId));
  }

  @Override
  @NonNull
  public VacationLeave updateVacationLeave(@NonNull final Long vacationLeaveId,
      @NonNull final VacationLeave updatedVacationLeave) {
    Objects.requireNonNull(vacationLeaveId, "VacationLeaveId should not be null here.");
    Objects.requireNonNull(updatedVacationLeave,
        "The updated vacation leave should not be null here.");

    VacationLeave vacationLeave = vacationLeaveRepository.findById(vacationLeaveId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(vacationLeaveId));

    vacationLeave.setStylistId(updatedVacationLeave.getStylistId());
    vacationLeave.setStart(updatedVacationLeave.getStart());
    vacationLeave.setEnd(updatedVacationLeave.getEnd());

    log.debug("Updating a vacation leave with id " + vacationLeaveId);
    return vacationLeaveRepository.saveAndFlush(vacationLeave);
  }

  @Override
  public void deleteVacationLeave(@NonNull final Long vacationLeaveId) {
    Objects.requireNonNull(vacationLeaveId, "VacationLeaveId should not be null here.");

    final VacationLeave vacationLeave = vacationLeaveRepository.findById(vacationLeaveId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(vacationLeaveId));

    log.debug("Deleting a vacation leave with id " + vacationLeaveId);
    vacationLeaveRepository.delete(vacationLeave);
  }
}
