package rs.raf.student.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public boolean isAuthorized(String token) {
        if (token == null || !token.startsWith("Bearer"))
            return false;

        DecodedJWT jwt = JWT.require(Algorithm.HMAC512("public-blog"))
                            .build()
                            .verify(token.replace("Bearer ", ""));

        return false;
    }

}
