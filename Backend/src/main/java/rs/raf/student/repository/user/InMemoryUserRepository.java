package rs.raf.student.repository.user;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import lombok.Getter;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class InMemoryUserRepository implements IUserRepository {

    private final List<User> users = new CopyOnWriteArrayList<>();

    @Inject
    private UserMapper mapper;

    @Inject
    private ICommentRepository commentRepository;

    @PostConstruct
    private void initialise() {
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
    public List<User> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId)
                                .stream()
                                .map(comment -> users.stream()
                                                     .filter(user -> user.getId().equals(comment.getAuthorId()))
                                                     .findFirst()
                                                     .orElse(null))
                                .toList();
    }

    @Override
    public Optional<User> create(UserCreateDto createDto) {
        User user = new User();

        synchronized (this) {
            user.setId((long) users.size());
            users.add(user);
        }

        return Optional.of(mapper.map(user, createDto));
    }

    @Override
    public Optional<User> update(UserUpdateDto updateDto) {
       return Utils.createStream(users.iterator())
                   .filter((item -> item.getId().equals(updateDto.getId())))
                   .map (item -> mapper.map(item, updateDto))
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
