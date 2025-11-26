package de.seuhd.campuscoffee.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record User (
        //TODO: Implement user domain object
        @Nullable Long id, // id is null when creating a new user
        @Nullable LocalDateTime createdAt, // is null when using DTO to create a new user
        @Nullable LocalDateTime updatedAt, // is set when creating or updating a user

        @NotNull
        @Size(min = 1, max = 255, message = "Login name must be between 1 and 255 characters long.")
        @Pattern(regexp = "\\w+", message = "Login name can only contain word characters: [a-zA-Z_0-9]+") // implies non-empty
        @NonNull String loginName,
        @NotNull
        @Email
        @NonNull String emailAddress,
        @NotNull
        @Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters long.")
        @NonNull String firstName,
        @NotNull
        @Size(min = 1, max = 255, message = "Last name must be between 1 and 255 characters long.")
        @NonNull String lastName
) implements Serializable { // serializable to allow cloning (see TestFixtures class).
    @Serial
    private static final long serialVersionUID = 1L;
}
