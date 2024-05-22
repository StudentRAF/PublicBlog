package rs.raf.student.repository.user;

import jakarta.inject.Inject;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryUserRepository implements IUserRepository {

    private final List<User> users = new CopyOnWriteArrayList<>();

    @Inject
    private UserMapper userMapper;

    public InMemoryUserRepository() {
        initialUsers.forEach(this::create);
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

    @Override
    public Optional<User> create(UserCreateDto createDto) {
        User user = new User();

        synchronized (this) {
            user.setId((long) users.size());
            users.add(user);
        }

        return Optional.of(userMapper.map(user, createDto));
    }

    @Override
    public Optional<User> update(UserUpdateDto updateDto) {
       return Utils.createStream(users.iterator())
                   .filter((item -> item.getId().equals(updateDto.getId())))
                   .map (item -> userMapper.map(item, updateDto))
                   .findFirst();
    }

    @Override
    public Optional<User> deleteById(Long id) {
        User user = Utils.createStream(users.iterator())
                         .filter((item -> item.getId().equals(id)))
                         .findFirst()
                         .orElse(null);

        if (user != null)
            users.remove(user);

        return Optional.ofNullable(user);
    }

}
