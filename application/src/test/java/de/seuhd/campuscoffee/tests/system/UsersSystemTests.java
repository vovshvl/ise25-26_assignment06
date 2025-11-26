package de.seuhd.campuscoffee.tests.system;

import de.seuhd.campuscoffee.api.dtos.PosDto;
import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.domain.model.Pos;
import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.tests.TestFixtures;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import static de.seuhd.campuscoffee.tests.SystemTestUtils.Requests.posRequests;
import static de.seuhd.campuscoffee.tests.SystemTestUtils.Requests.userRequests;
import static org.assertj.core.api.Assertions.assertThat;

public class UsersSystemTests extends AbstractSysTest {

    //TODO: Uncomment once user endpoint is implemented

    @Test
    void createUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();
        User createdUser = userDtoMapper.toDomain(userRequests.create(List.of(userDtoMapper.fromDomain(userToCreate))).getFirst());

        assertEqualsIgnoringIdAndTimestamps(createdUser, userToCreate);
    }



    //TODO: Add at least two additional tests for user operations
    @Test
    void getUserById() {
        List<User> createdUserList = TestFixtures.createUsers(userService);
        User createdUser = createdUserList.getFirst();

        User retrievedUser = userDtoMapper.toDomain(
                userRequests.retrieveById(createdUser.id())
        );

        assertEqualsIgnoringTimestamps(retrievedUser, createdUser);
    }

    @Test
    void deleteUser() {
        List<User> createdUserList = TestFixtures.createUsers(userService);
        User userToDelete = createdUserList.getFirst();
        Objects.requireNonNull(userToDelete.id());

        List<Integer> statusCodes = userRequests.deleteAndReturnStatusCodes(List.of(userToDelete.id(), userToDelete.id()));

        // first deletion should return 204 No Content, second should return 404 Not Found
        assertThat(statusCodes)
                .containsExactly(HttpStatus.NO_CONTENT.value(), HttpStatus.NOT_FOUND.value());

        List<Long> remainingUserIds = userRequests.retrieveAll()
                .stream()
                .map(UserDto::id)
                .toList();
        assertThat(remainingUserIds)
                .doesNotContain(userToDelete.id());
    }

}