package com.hristofor.mirchev.outfittery.challenge.reservedtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.Appointment;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTimeRepository;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.SickLeave;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.VacationLeave;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservedTimeRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ReservedTimeRepository reservedTimeRepository;

  @Test
  public void getReservedTimesBetween_cutOutTimesOutsideRange() {
    // arrange
    final OffsetDateTime now = OffsetDateTime.parse("2018-04-30T10:00:00Z");

    final SickLeave sickLeave = new SickLeave();
    sickLeave.setStylistId(1L);
    sickLeave.setStart(now.minusMinutes(30));
    sickLeave.setEnd(now);

    final VacationLeave vacationLeave = new VacationLeave();
    vacationLeave.setStylistId(1L);
    vacationLeave.setStart(now);
    vacationLeave.setEnd(now.plusMinutes(30));

    final Appointment appointment = new Appointment();
    appointment.setStylistId(1L);
    appointment.setUserId(1L);
    appointment.setStart(now.plusMinutes(30));
    appointment.setEnd(now.plusMinutes(60));

    entityManager.persist(sickLeave);
    entityManager.persist(vacationLeave);
    entityManager.persist(appointment);
    entityManager.flush();

    // act
    final List<ReservedTime> actual = reservedTimeRepository
        .getReservedTimesBetween(now, now.plusMinutes(60));
    // we expect to cut out the sick leave
    final List<ReservedTime> expected = Arrays.asList(vacationLeave, appointment);

    // assert
    assertEquals(actual, expected);
  }

  @Test
  public void getReservedTimesBetween_emptyListIfNone() {
    // arrange
    final OffsetDateTime now = OffsetDateTime.parse("2018-04-30T10:00:00Z");

    final SickLeave sickLeave = new SickLeave();
    sickLeave.setStylistId(1L);
    sickLeave.setStart(now.minusMinutes(30));
    sickLeave.setEnd(now);

    final VacationLeave vacationLeave = new VacationLeave();
    vacationLeave.setStylistId(1L);
    vacationLeave.setStart(now);
    vacationLeave.setEnd(now.plusMinutes(30));

    final Appointment appointment = new Appointment();
    appointment.setStylistId(1L);
    appointment.setUserId(1L);
    appointment.setStart(now.plusMinutes(30));
    appointment.setEnd(now.plusMinutes(60));

    entityManager.persist(sickLeave);
    entityManager.persist(vacationLeave);
    entityManager.persist(appointment);
    entityManager.flush();

    // act
    final List<ReservedTime> actual = reservedTimeRepository
        .getReservedTimesBetween(now.plusMinutes(60), now.plusMinutes(120));

    // assert
    assertTrue(actual.isEmpty());
  }

  @Test
  public void getReservedTimesOfStylistThatOverlapWith_cutOutTimesOutsideRange() {
    // arrange
    final OffsetDateTime now = OffsetDateTime.parse("2018-04-30T10:00:00Z");

    final SickLeave sickLeave = new SickLeave();
    sickLeave.setStylistId(1L);
    sickLeave.setStart(now.minusMinutes(30));
    sickLeave.setEnd(now);

    final VacationLeave vacationLeave = new VacationLeave();
    vacationLeave.setStylistId(1L);
    vacationLeave.setStart(now);
    vacationLeave.setEnd(now.plusMinutes(30));

    final Appointment appointment = new Appointment();
    appointment.setStylistId(1L);
    appointment.setUserId(1L);
    appointment.setStart(now.plusMinutes(30));
    appointment.setEnd(now.plusMinutes(60));

    entityManager.persist(sickLeave);
    entityManager.persist(vacationLeave);
    entityManager.persist(appointment);
    entityManager.flush();

    // act
    final List<ReservedTime> actual = reservedTimeRepository
        .getReservedTimesOfStylistThatOverlapWith(1L, now.plusMinutes(15), now.plusMinutes(45));
    // both the vacation leave: [now, +30] and appointment: [+30, +60] are in the range [+15, +45]
    final List<ReservedTime> expected = Arrays.asList(vacationLeave, appointment);

    // assert
    assertEquals(actual, expected);
  }

  @Test
  public void getReservedTimesOfStylistThatOverlapWith_emptyListIfNone() {
    // arrange
    final OffsetDateTime now = OffsetDateTime.parse("2018-04-30T10:00:00Z");

    final SickLeave sickLeave = new SickLeave();
    sickLeave.setStylistId(1L);
    sickLeave.setStart(now.minusMinutes(30));
    sickLeave.setEnd(now);

    final VacationLeave vacationLeave = new VacationLeave();
    vacationLeave.setStylistId(1L);
    vacationLeave.setStart(now);
    vacationLeave.setEnd(now.plusMinutes(30));

    final Appointment appointment = new Appointment();
    appointment.setStylistId(1L);
    appointment.setUserId(1L);
    appointment.setStart(now.plusMinutes(30));
    appointment.setEnd(now.plusMinutes(60));

    entityManager.persist(sickLeave);
    entityManager.persist(vacationLeave);
    entityManager.persist(appointment);
    entityManager.flush();

    // act
    final List<ReservedTime> actual = reservedTimeRepository
        .getReservedTimesOfStylistThatOverlapWith(1L, now.minusMinutes(60), now.minusMinutes(120));

    // assert
    assertTrue(actual.isEmpty());
  }
}
