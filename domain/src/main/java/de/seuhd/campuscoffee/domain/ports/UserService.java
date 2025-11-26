package de.seuhd.campuscoffee.domain.ports;

import de.seuhd.campuscoffee.domain.model.Pos;
import de.seuhd.campuscoffee.domain.model.User;
import org.jspecify.annotations.NonNull;

import java.util.List;

public interface UserService {
    //TODO: Define user service interface
    @NonNull List<User> getAll();
    @NonNull User upsert(@NonNull User user);
    @NonNull User getById(@NonNull Long id);
    @NonNull User getByName(@NonNull String name);
    void delete(@NonNull Long id);
    void clear();
}
