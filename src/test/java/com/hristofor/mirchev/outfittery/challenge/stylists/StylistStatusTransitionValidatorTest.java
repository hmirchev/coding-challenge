package com.hristofor.mirchev.outfittery.challenge.stylists;

import static com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatusTransitionValidator.isTransitionValid;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import org.junit.Test;

public class StylistStatusTransitionValidatorTest {

  @Test
  public void isTransitionValid_allTransitions() {
    // from ROOKIE
    assertFalse(isTransitionValid(StylistStatus.ROOKIE, StylistStatus.ROOKIE));
    assertTrue(isTransitionValid(StylistStatus.ROOKIE, StylistStatus.READY_TO_STYLE));
    assertFalse(isTransitionValid(StylistStatus.ROOKIE, StylistStatus.SICK));
    assertFalse(isTransitionValid(StylistStatus.ROOKIE, StylistStatus.ON_HOLIDAYS));
    assertFalse(isTransitionValid(StylistStatus.ROOKIE, StylistStatus.OFFBOARDED));

    // from READY_TO_STYLE
    assertFalse(isTransitionValid(StylistStatus.READY_TO_STYLE, StylistStatus.ROOKIE));
    assertFalse(isTransitionValid(StylistStatus.READY_TO_STYLE, StylistStatus.READY_TO_STYLE));
    assertTrue(isTransitionValid(StylistStatus.READY_TO_STYLE, StylistStatus.SICK));
    assertTrue(isTransitionValid(StylistStatus.READY_TO_STYLE, StylistStatus.ON_HOLIDAYS));
    assertTrue(isTransitionValid(StylistStatus.READY_TO_STYLE, StylistStatus.OFFBOARDED));

    // from SICK
    assertFalse(isTransitionValid(StylistStatus.SICK, StylistStatus.ROOKIE));
    assertTrue(isTransitionValid(StylistStatus.SICK, StylistStatus.READY_TO_STYLE));
    assertTrue(isTransitionValid(StylistStatus.SICK, StylistStatus.SICK));
    assertFalse(isTransitionValid(StylistStatus.SICK, StylistStatus.ON_HOLIDAYS));
    assertFalse(isTransitionValid(StylistStatus.SICK, StylistStatus.OFFBOARDED));

    // from ON_HOLIDAYS
    assertFalse(isTransitionValid(StylistStatus.ON_HOLIDAYS, StylistStatus.ROOKIE));
    assertTrue(isTransitionValid(StylistStatus.ON_HOLIDAYS, StylistStatus.READY_TO_STYLE));
    assertFalse(isTransitionValid(StylistStatus.ON_HOLIDAYS, StylistStatus.SICK));
    assertTrue(isTransitionValid(StylistStatus.ON_HOLIDAYS, StylistStatus.ON_HOLIDAYS));
    assertFalse(isTransitionValid(StylistStatus.ON_HOLIDAYS, StylistStatus.OFFBOARDED));

    // from OFFBOARDED
    assertFalse(isTransitionValid(StylistStatus.OFFBOARDED, StylistStatus.ROOKIE));
    assertFalse(isTransitionValid(StylistStatus.OFFBOARDED, StylistStatus.READY_TO_STYLE));
    assertFalse(isTransitionValid(StylistStatus.OFFBOARDED, StylistStatus.SICK));
    assertFalse(isTransitionValid(StylistStatus.OFFBOARDED, StylistStatus.ON_HOLIDAYS));
    assertFalse(isTransitionValid(StylistStatus.OFFBOARDED, StylistStatus.OFFBOARDED));
  }
}
