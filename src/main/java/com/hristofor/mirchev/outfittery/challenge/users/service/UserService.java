package com.hristofor.mirchev.outfittery.challenge.users.service;

import com.hristofor.mirchev.outfittery.challenge.users.repository.User;
import com.hristofor.mirchev.outfittery.challenge.users.repository.UserNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {

    /**
     * Gets a list of all {@link User}.
     *
     * @return a list of all {@link User} or empty list, if there are none.
     */
    @NonNull
    List<User> getAllUsers();

    /**
     * Creates a new {@link User}.
     *
     * @param user the {@link User} to create.
     * @return the newly created instance of a {@link User}.
     */
    @NonNull
    User createUser(@NonNull User user);

    /**
     * Gets the {@link User} with the provided id.
     *
     * @param userId the id of the {@link User} we want to get.
     * @return the {@link User} with the provided id.
     * @throws UserNotFoundException when a {@link User} with the provided id does not exist.
     */
    @NonNull
    User getUserById(@NonNull Long userId) throws UserNotFoundException;

    /**
     * Deletes the {@link User} with the provided id.
     *
     * @param userId the id of the {@link User} we want to delete.
     * @throws UserNotFoundException when a {@link User} with the provided id does not exist.
     */
    void delete(@NonNull Long userId) throws UserNotFoundException;

    /**
     * Updates the {@link User} with the provided id.
     *
     * <p> Note, the updated {@link User} overwrites the old one.
     *
     * @param userId      the id of the {@link User} we want to update.
     * @param updatedUser the new data for the {@link User}.
     * @return the newly updated instance of the {@link User}.
     * @throws UserNotFoundException when a {@link User} with the provided id does not exist.
     */
    @NonNull
    User updateUser(@NonNull Long userId, @NonNull User updatedUser) throws UserNotFoundException;
}
