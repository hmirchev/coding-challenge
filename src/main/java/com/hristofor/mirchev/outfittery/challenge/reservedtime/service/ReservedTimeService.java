package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.CallOrder;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.SickLeave;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.Vacation;
import org.springframework.lang.NonNull;

import java.time.OffsetDateTime;
import java.util.List;

public interface ReservedTimeService {

    /**
     * Gets a list of all {@link ReservedTime}.
     *
     * @return a list of all {@link ReservedTime} or empty list, if there are none.
     */
    @NonNull
    List<ReservedTime> getAllReservedTime();

    /**
     * Creates a new {@link SickLeave} instance of {@link ReservedTime}.
     *
     * @param start     the start date time for the sick leave we want to create.
     * @param end       the end date time for the sick leave we want to create.
     * @param stylistId the id of the stylist whose sick leave we want to create.
     * @return the newly created instance of a {@link SickLeave}.
     */
    @NonNull
    SickLeave createSickLeave(@NonNull OffsetDateTime start, @NonNull OffsetDateTime end, @NonNull Long stylistId);

    /**
     * Creates a new {@link Vacation} instance of {@link ReservedTime}.
     *
     * @param start     the start date time for the sick leave we want to create.
     * @param end       the end date time for the sick leave we want to create.
     * @param stylistId the id of the stylist whose sick leave we want to create.
     * @return the newly created instance of a {@link Vacation}.
     */
    @NonNull
    Vacation createVacation(@NonNull OffsetDateTime start, @NonNull OffsetDateTime end, @NonNull Long stylistId);

    /**
     * Creates a new {@link CallOrder} instance of {@link ReservedTime}.
     *
     * @param start     the start date time for the sick leave we want to create.
     * @param end       the end date time for the sick leave we want to create.
     * @param stylistId the id of the stylist whose sick leave we want to create.
     * @return the newly created instance of a {@link CallOrder}.
     */
    @NonNull
    CallOrder createCallOrder(@NonNull OffsetDateTime start, @NonNull OffsetDateTime end,
                              @NonNull Long stylistId, @NonNull Long userId);
}
