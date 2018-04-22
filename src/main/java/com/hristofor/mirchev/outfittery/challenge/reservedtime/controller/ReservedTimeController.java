package com.hristofor.mirchev.outfittery.challenge.reservedtime.controller;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.CallOrder;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.SickLeave;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.Vacation;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.ReservedTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@Api(description = "Operations regarding the reserved times.")
@RestController
@RequestMapping("/v1")
public class ReservedTimeController {

    @Autowired
    private ReservedTimeService reservedTimeService;

    @ApiOperation(value = "Gets a list of all stylists.", response = List.class)
    @GetMapping(value = "/reservedTimes", produces = "application/json")
    public List<ReservedTime> getAllReservedTimes() {
        return reservedTimeService.getAllReservedTime();
    }

    @ApiOperation(value = "Creates a new sick leave.", response = SickLeave.class)
    @PostMapping(value = "/sickLeaves", produces = "application/json")
    public SickLeave createSickLeave(@RequestParam(value = "start") String start,
                                     @RequestParam(value = "end") String end,
                                     @RequestParam(value = "stylistId") Long stylistId) {

        return reservedTimeService.createSickLeave(OffsetDateTime.parse(start), OffsetDateTime.parse(end), stylistId);
    }

    @ApiOperation(value = "Creates a new vacation.", response = Vacation.class)
    @PostMapping(value = "/vacations", produces = "application/json")
    public Vacation createVacation(@RequestParam(value = "start") String start,
                                   @RequestParam(value = "end") String end,
                                   @RequestParam(value = "stylistId") Long stylistId) {

        return reservedTimeService.createVacation(OffsetDateTime.parse(start), OffsetDateTime.parse(end), stylistId);
    }

    @ApiOperation(value = "Creates a new call order.", response = CallOrder.class)
    @PostMapping(value = "/callOrders", produces = "application/json")
    public CallOrder createCallOrder(@RequestParam(value = "start") String start,
                                     @RequestParam(value = "end") String end,
                                     @RequestParam(value = "stylistId") Long stylistId,
                                     @RequestParam(value = "userId") Long userId) {

        return reservedTimeService.createCallOrder(OffsetDateTime.parse(start), OffsetDateTime.parse(end), stylistId, userId);
    }

    // TODO: Multiple createCallOrders
}
