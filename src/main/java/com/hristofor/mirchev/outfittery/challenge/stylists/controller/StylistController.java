package com.hristofor.mirchev.outfittery.challenge.stylists.controller;

import com.hristofor.mirchev.outfittery.challenge.stylists.repository.Stylist;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatusWrapper;
import com.hristofor.mirchev.outfittery.challenge.stylists.service.StylistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Api(description = "Operations regarding the stylists.")
@RestController
@RequestMapping("/v1")
public class StylistController {

    @Autowired
    private StylistService stylistService;

    @ApiOperation(value = "Gets a list of all stylists.", response = List.class)
    @GetMapping(value = "/stylists", produces = "application/json")
    public List<Stylist> getAllStylists() {
        return stylistService.getAllStylists();
    }

    @ApiOperation(value = "Creates a new stylist.", response = Stylist.class)
    @PostMapping(value = "/stylists", produces = "application/json")
    public Stylist createStylist(@Valid @RequestBody Stylist stylist) {
        return stylistService.createStylist(stylist);
    }

    @ApiOperation(value = "Gets the stylist with the provided id.", response = Stylist.class)
    @GetMapping(value = "/stylists/{id}", produces = "application/json")
    public Stylist getStylistById(@PathVariable(value = "id") Long stylistId) {
        return stylistService.getStylistById(stylistId);
    }

    @ApiOperation(value = "Deletes the stylist with the provided id.")
    @DeleteMapping(value = "/stylists/{id}", produces = "application/json")
    public ResponseEntity<?> deleteStylist(@PathVariable(value = "id") Long stylistId) {
        stylistService.deleteStylist(stylistId);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Updates the stylist with the provided id.", response = Stylist.class)
    @PutMapping(value = "/stylists/{id}", produces = "application/json")
    public Stylist updateStylist(@PathVariable(value = "id") Long stylistId, @Valid @RequestBody Stylist updatedStylist) {
        return stylistService.updateStylist(stylistId, updatedStylist);
    }

    @ApiOperation(value = "Changes the status of the stylist with the provided id.", response = Stylist.class)
    @PatchMapping(value = "/stylists/{id}", produces = "application/json")
    public Stylist updateStylistStatus(@PathVariable(value = "id") Long stylistId, @RequestBody StylistStatusWrapper newStylistStatus) {
        Objects.requireNonNull(newStylistStatus.getStatus(), "StylistStatus should not be null here.");
        return stylistService.changeStylistStatus(stylistId, newStylistStatus.getStatus());
    }
}
