package rs.raf.student.repository;

import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.model.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> create(UserCreateDto createDto);

    Optional<User> update(UserUpdateDto updateDto);

    Optional<User> deleteById(Long id);

}
