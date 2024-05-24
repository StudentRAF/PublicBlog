package rs.raf.student.repository;

import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.model.User;
import rs.raf.student.utils.Utils;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<UserCreateDto> initialUsers = List.of(
            new UserCreateDto("Graeme", "Tabatha",  "Admin",     "admin"),
            new UserCreateDto("Toni",   "Maystone", "TMayStone", "tmaystone"),
            new UserCreateDto("Daisy",  "Horsell",  "DHorsell",  "dhorsell")
    );

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAllByPostId(Long postId);

    Optional<User> create(UserCreateDto createDto);

    Optional<User> update(UserUpdateDto updateDto);

    Optional<User> deleteById(Long id);

}
