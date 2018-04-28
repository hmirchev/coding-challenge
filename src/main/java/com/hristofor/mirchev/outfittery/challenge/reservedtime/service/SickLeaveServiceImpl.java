package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.exceptions.ReservedTimeNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.SickLeave;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.SickLeaveRepository;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SickLeaveServiceImpl implements SickLeaveService {

  private static final Logger log = LoggerFactory.getLogger(SickLeaveServiceImpl.class);

  @Autowired
  private SickLeaveRepository sickLeaveRepository;

  @Override
  @NonNull
  public SickLeave createSickLeave(@NonNull final SickLeave sickLeave) {
    Objects.requireNonNull(sickLeave, "Sick leave should not be null here.");

    log.debug("Creating a sick leave");
    return sickLeaveRepository.saveAndFlush(sickLeave);
  }

  @Override
  @NonNull
  public List<SickLeave> getAllSickLeaves() {
    log.debug("Getting all sick leaves");
    return sickLeaveRepository.findAll();
  }

  @Override
  @NonNull
  public SickLeave getSickLeaveById(@NonNull final Long sickLeaveId) {
    Objects.requireNonNull(sickLeaveId, "SickLeaveId should not be null here.");

    log.debug("Getting a sick leave with id " + sickLeaveId);

    return sickLeaveRepository.findById(sickLeaveId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(sickLeaveId));
  }

  @Override
  @NonNull
  public SickLeave updateSickLeave(@NonNull final Long sickLeaveId,
      @NonNull final SickLeave updatedSickLeave) {
    Objects.requireNonNull(sickLeaveId, "SickLeaveId should not be null here.");
    Objects.requireNonNull(updatedSickLeave, "The updated sick leave should not be null here.");

    SickLeave sickLeave = sickLeaveRepository.findById(sickLeaveId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(sickLeaveId));
    sickLeave.setStylistId(updatedSickLeave.getStylistId());
    sickLeave.setStart(updatedSickLeave.getStart());
    sickLeave.setEnd(updatedSickLeave.getEnd());

    log.debug("Updating a sick leave with id " + sickLeaveId);
    return sickLeaveRepository.saveAndFlush(sickLeave);
  }

  @Override
  public void deleteSickLeave(@NonNull final Long sickLeaveId) {
    Objects.requireNonNull(sickLeaveId, "SickLeaveId should not be null here.");

    final SickLeave sickLeave = sickLeaveRepository.findById(sickLeaveId)
        .orElseThrow(() -> new ReservedTimeNotFoundException(sickLeaveId));

    log.debug("Deleting a sick leave with id " + sickLeaveId);
    sickLeaveRepository.delete(sickLeave);
  }
}
