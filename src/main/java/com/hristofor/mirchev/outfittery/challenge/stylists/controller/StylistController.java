package com.hristofor.mirchev.outfittery.challenge.stylists.controller;

import com.hristofor.mirchev.outfittery.challenge.stylists.dtos.DTOtoEntityConverter;
import com.hristofor.mirchev.outfittery.challenge.stylists.dtos.StylistDTO;
import com.hristofor.mirchev.outfittery.challenge.stylists.dtos.StylistStatusOnlyDTO;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.Stylist;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import com.hristofor.mirchev.outfittery.challenge.stylists.service.StylistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(description = "Operations regarding the stylists.")
@RestController
@RequestMapping("/v1")
public class StylistController {

  @Autowired
  private StylistService stylistService;

  @ApiOperation(value = "Creates a new stylist. Note, we disregard the provided StylistStatus" +
      "and assign ROOKIE, as on creation this status is the only one possible", response = StylistDTO.class)
  @PostMapping(value = "/stylists", produces = "application/json")
  public ResponseEntity<?> createStylist(@Valid @RequestBody StylistDTO stylist) {

    final Stylist transientStylist = DTOtoEntityConverter.convert(stylist);
    // when creating a stylist we can only assign a StylistStatus of ROOKIE, according to the state machine
    transientStylist.setStatus(StylistStatus.ROOKIE);

    final Stylist persistentStylist = stylistService.createStylist(transientStylist);

    // setting the location header to point to the created user
    final URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}").buildAndExpand(persistentStylist.getId()).toUri();

    return ResponseEntity.created(location).body(DTOtoEntityConverter.convert(persistentStylist));
  }

  @ApiOperation(value = "Gets a list of all stylists.", response = List.class)
  @GetMapping(value = "/stylists", produces = "application/json")
  public List<StylistDTO> getAllStylists() {

    return stylistService.getAllStylists().stream().map(DTOtoEntityConverter::convert)
        .collect(Collectors.toList());
  }

  @ApiOperation(value = "Gets the stylist with the provided id.", response = StylistDTO.class)
  @ApiResponse(code = 404, message = "The stylist with the provided id could not be found.")
  @GetMapping(value = "/stylists/{id}", produces = "application/json")
  public StylistDTO getStylistById(@PathVariable(value = "id") Long stylistId) {

    final Stylist stylist = stylistService.getStylistById(stylistId);
    return DTOtoEntityConverter.convert(stylist);
  }

  @ApiOperation(value = "Gets the stylists with the provided list of ids.", response = List.class)
  @ApiResponse(code = 404, message = "The stylist with the provided id could not be found.")
  @GetMapping(value = "/stylists/{ids}", produces = "application/json")
  public List<StylistDTO> getStylistsByIds(@PathVariable(value = "ids") List<Long> stylistIds) {

    final List<Stylist> stylist = stylistService.getStylistsByIds(stylistIds);
    return stylist.stream().map(DTOtoEntityConverter::convert).collect(Collectors.toList());
  }

  @ApiOperation(value = "Updates the stylist with the provided id.", response = StylistDTO.class)
  @ApiResponse(code = 404, message = "The stylist with the provided id could not be found.")
  @PutMapping(value = "/stylists/{id}", produces = "application/json")
  public StylistDTO updateStylist(@PathVariable(value = "id") Long stylistId,
      @Valid @RequestBody StylistDTO updatedStylist) {

    final Stylist transientUpdatedStylist = DTOtoEntityConverter.convert(updatedStylist);

    final Stylist persistentUpdatedStylist = stylistService
        .updateStylist(stylistId, transientUpdatedStylist);

    return DTOtoEntityConverter.convert(persistentUpdatedStylist);
  }

  @ApiOperation(value = "Changes the status of the stylist with the provided id.", response = StylistDTO.class)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "The new stylist status is an invalid transition from the old one."),
      @ApiResponse(code = 404, message = "The stylist with the provided id could not be found.")
  })
  @PatchMapping(value = "/stylists/{id}", produces = "application/json")
  public StylistDTO updateStylistStatus(@PathVariable(value = "id") Long stylistId,
      @RequestBody StylistStatusOnlyDTO newStylistStatus) {
    Objects.requireNonNull(newStylistStatus.getStatus(), "StylistStatus should not be null here.");

    final Stylist updatedStylist = stylistService
        .changeStylistStatus(stylistId, newStylistStatus.getStatus());

    return DTOtoEntityConverter.convert(updatedStylist);
  }

  @ApiOperation(value = "Deletes the stylist with the provided id.")
  @ApiResponse(code = 404, message = "The stylist with the provided id could not be found.")
  @DeleteMapping(value = "/stylists/{id}", produces = "application/json")
  public ResponseEntity<?> deleteStylist(@PathVariable(value = "id") Long stylistId) {
    stylistService.deleteStylist(stylistId);

    return ResponseEntity.ok().build();
  }
}
