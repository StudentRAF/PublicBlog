package rs.raf.student.repository;

import rs.raf.student.model.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

}
