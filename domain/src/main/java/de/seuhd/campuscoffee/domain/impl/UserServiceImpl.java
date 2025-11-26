package de.seuhd.campuscoffee.domain.impl;

import de.seuhd.campuscoffee.domain.exceptions.DuplicationException;
import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.ports.UserDataService;
import de.seuhd.campuscoffee.domain.ports.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDataService userDataService;

    // TODO: Implement user service
    @Override
    public @NonNull List<User> getAll() {
        log.debug("Retrieving all users");
        return userDataService.getAll();
    }


    @Override
    public @NonNull User upsert(@NonNull User user) {
        if (user.id() == null) {
            // create a new POS
            log.info("Creating new User: {}", user.firstName());
        } else {
            // update an existing POS
            log.info("Updating POS with ID: {}", user.id());
            // POS ID must be set
            Objects.requireNonNull(user.id());
            // POS must exist in the database before the update
            userDataService.getById(user.id());
        }
        return performUpsert(user);
    }

    private @NonNull User performUpsert(@NonNull User user) {
        try {
            User upsertedUser = userDataService.upsert(user);
            log.info("Successfully upserted User with ID: {}", upsertedUser.id());
            return upsertedUser;
        } catch (DuplicationException e) {
            log.error("Error upserting User '{}': {}", user.firstName(), e.getMessage());
            throw e;
        }
    }

    @Override
    public @NonNull User getById(@NonNull Long id) {
        log.debug("Retrieving User with ID: {}", id);
        return userDataService.getById(id);
    }

    @Override
    public @NonNull User getByName(@NonNull String name) {
        log.debug("Retrieving User with name: {}", name);
        return userDataService.getByLoginName(name);
    }

    @Override
    public void delete(@NonNull Long id) {
        log.info("Trying to delete POS with ID: {}", id);
        userDataService.delete(id);
        log.info("Deleted POS with ID: {}", id);
    }

    @Override
    public void clear() {
        log.warn("Clearing all POS data");
        userDataService.clear();
    }
}
