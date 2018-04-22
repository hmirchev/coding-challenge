package com.hristofor.mirchev.outfittery.challenge.reservedtime.service;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.*;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.Stylist;
import com.hristofor.mirchev.outfittery.challenge.stylists.service.StylistService;
import com.hristofor.mirchev.outfittery.challenge.users.repository.User;
import com.hristofor.mirchev.outfittery.challenge.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReservedTimeServiceImpl implements ReservedTimeService {

    private static final Logger log = LoggerFactory.getLogger(ReservedTimeServiceImpl.class);

    @Autowired
    private ReservedTimeRepository reservedTimeRepository;

    @Autowired
    private SickLeaveRepository sickLeaveRepository;

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private CallOrderRepository callOrderRepository;

    @Autowired
    private StylistService stylistService;

    @Autowired
    private UserService userService;

    @Override
    @NonNull
    public List<ReservedTime> getAllReservedTime() {
        log.debug("Getting all reserved times");
        return reservedTimeRepository.findAll();
    }

    @Override
    @NonNull
    public SickLeave createSickLeave(@NonNull final OffsetDateTime start, @NonNull final OffsetDateTime end,
                                     @NonNull final Long stylistId) {

        Objects.requireNonNull(start, "The start should not be null here.");
        Objects.requireNonNull(end, "The end should not be null here.");
        Objects.requireNonNull(stylistId, "The stylistId should not be null here.");

        final Stylist stylist = stylistService.getStylistById(stylistId);
        final SickLeave sickLeave = new SickLeave();
        sickLeave.setStart(start);
        sickLeave.setEnd(end);
        sickLeave.setStylist(stylist);

        log.debug("Creating a SickLeave for Stylist " + stylistId);
        return sickLeaveRepository.saveAndFlush(sickLeave);
    }

    @Override
    @NonNull
    public Vacation createVacation(@NonNull final OffsetDateTime start, @NonNull final OffsetDateTime end,
                                   @NonNull final Long stylistId) {

        Objects.requireNonNull(start, "The start should not be null here.");
        Objects.requireNonNull(end, "The end should not be null here.");
        Objects.requireNonNull(stylistId, "The stylistId should not be null here.");

        final Stylist stylist = stylistService.getStylistById(stylistId);

        Vacation vacation = new Vacation();
        vacation.setStart(start);
        vacation.setEnd(end);
        vacation.setStylist(stylist);

        log.debug("Creating a vacation for Stylist " + stylistId);
        return vacationRepository.saveAndFlush(vacation);
    }

    @Override
    @NonNull
    public CallOrder createCallOrder(@NonNull final OffsetDateTime start, @NonNull final OffsetDateTime end,
                                     @NonNull final Long stylistId, @NonNull final Long userId) {

        Objects.requireNonNull(start, "The start should not be null here.");
        Objects.requireNonNull(end, "The end should not be null here.");
        Objects.requireNonNull(stylistId, "The stylistId should not be null here.");
        Objects.requireNonNull(userId, "The userId should not be null here.");

        final Stylist stylist = stylistService.getStylistById(stylistId);
        final User user = userService.getUserById(userId);

        final CallOrder callOrder = new CallOrder();
        callOrder.setStart(start);
        callOrder.setEnd(end);
        callOrder.setStylist(stylist);
        callOrder.setUser(user);

        log.debug("Creating a call order for Stylist " + stylistId + " with User " + userId);
        return callOrderRepository.saveAndFlush(callOrder);
    }
}
