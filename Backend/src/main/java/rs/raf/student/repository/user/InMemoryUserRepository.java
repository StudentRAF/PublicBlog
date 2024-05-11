package rs.raf.student.repository.user;

import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryUserRepository implements IUserRepository {

    private final List<User> users = new CopyOnWriteArrayList<>();

    public InMemoryUserRepository() {
        users.add(new User(1L, "Name", "LastName", "Username", "password"));
    }

    @Override
    public Optional<User> findById(Long id) {
        return Utils.createStream(users.iterator())
                    .filter(user -> user.getId().equals(id))
                    .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Utils.createStream(users.iterator())
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst();
    }

}
