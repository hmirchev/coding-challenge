package com.hristofor.mirchev.outfittery.challenge.stylists.repository;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import java.util.Objects;
import org.springframework.lang.NonNull;

public class StylistStatusTransitionValidator {

  private static SetMultimap<StylistStatus, StylistStatus> validTransitions;

  static {
    validTransitions = HashMultimap.create();

    validTransitions.put(StylistStatus.ROOKIE, StylistStatus.READY_TO_STYLE);
    validTransitions.put(StylistStatus.READY_TO_STYLE, StylistStatus.SICK);
    validTransitions.put(StylistStatus.READY_TO_STYLE, StylistStatus.ON_HOLIDAYS);
    validTransitions.put(StylistStatus.READY_TO_STYLE, StylistStatus.OFFBOARDED);
    validTransitions.put(StylistStatus.SICK, StylistStatus.SICK);
    validTransitions.put(StylistStatus.SICK, StylistStatus.READY_TO_STYLE);
    validTransitions.put(StylistStatus.ON_HOLIDAYS, StylistStatus.ON_HOLIDAYS);
    validTransitions.put(StylistStatus.ON_HOLIDAYS, StylistStatus.READY_TO_STYLE);
  }

  public static boolean isTransitionValid(@NonNull final StylistStatus from,
      @NonNull final StylistStatus to) {
    Objects.requireNonNull(from, "From StylistStatus should not be null here.");
    Objects.requireNonNull(to, "To StylistStatus should not be null here.");

    return validTransitions.get(from).contains(to);
  }
}
