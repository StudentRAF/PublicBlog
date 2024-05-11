package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.dto.user.UserLoginDto;
import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;

public class UserService {

    @Inject
    private IUserRepository repository;

    public User getUserById(Long id) {
        return repository.findById(id)
                         .orElse(null);
    }

    public User login(UserLoginDto loginDto) {
        User user = repository.findByUsername(loginDto.getUsername())
                              .orElse(null);

        if (user == null || !user.getPassword().equals(loginDto.getPassword()))
            return null;

        return user;
    }

}
