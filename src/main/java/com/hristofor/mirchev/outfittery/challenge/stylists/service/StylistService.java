package com.hristofor.mirchev.outfittery.challenge.stylists.service;

import com.hristofor.mirchev.outfittery.challenge.stylists.repository.Stylist;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistNotFoundException;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;
import org.springframework.lang.NonNull;

import java.util.List;

public interface StylistService {

    /**
     * Gets a list of all {@link Stylist}.
     *
     * @return a list of all {@link Stylist} or empty list, if there are none.
     */
    @NonNull
    List<Stylist> getAllStylists();

    /**
     * Creates a new {@link Stylist}.
     *
     * @param stylist the {@link Stylist} to create.
     * @return the newly created instance of a {@link Stylist}.
     */
    @NonNull
    Stylist createStylist(@NonNull Stylist stylist);

    /**
     * Gets the {@link Stylist} with the provided id.
     *
     * @param stylistId the id of the {@link Stylist} we want to get.
     * @return the {@link Stylist} with the provided id.
     * @throws StylistNotFoundException when a {@link Stylist} with the provided id does not exist.
     */
    @NonNull
    Stylist getStylistById(@NonNull Long stylistId) throws StylistNotFoundException;

    /**
     * Deletes the {@link Stylist} with the provided id.
     *
     * @param stylistId the id of the {@link Stylist} we want to delete.
     * @throws StylistNotFoundException when a {@link Stylist} with the provided id does not exist.
     */
    void deleteStylist(@NonNull Long stylistId) throws StylistNotFoundException;

    /**
     * Updates the {@link Stylist} with the provided id.
     *
     * <p> Note, the updated {@link Stylist} overwrites the old one.
     *
     * @param stylistId      the id of the {@link Stylist} we want to update.
     * @param updatedStylist the new data for the {@link Stylist}.
     * @return the newly updated instance of the {@link Stylist}.
     * @throws StylistNotFoundException when a {@link Stylist} with the provided id does not exist.
     */
    @NonNull
    Stylist updateStylist(@NonNull Long stylistId, @NonNull Stylist updatedStylist) throws StylistNotFoundException;

    /**
     * Changes the {@link StylistStatus} of the {@link Stylist} with the provided id.
     *
     * @param stylistId     the id of the {@link Stylist} whose {@link StylistStatus} we want to change.
     * @param stylistStatus the new {@link StylistStatus}.
     * @return the newly updated instance of the {@link Stylist}.
     * @throws StylistNotFoundException when a {@link Stylist} with the provided id does not exist.
     */
    @NonNull
    Stylist changeStylistStatus(@NonNull Long stylistId, @NonNull StylistStatus stylistStatus) throws StylistNotFoundException;
}
