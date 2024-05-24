package rs.raf.student.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import rs.raf.student.dto.user.UserGetDto;
import rs.raf.student.dto.user.UserLoginDto;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.utils.Utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserService {

    @Inject
    private IUserRepository repository;

    @Inject
    private UserMapper mapper;

    private final Algorithm algorithm = Algorithm.HMAC512("public-blog");

    public UserGetDto getUserById(Long id) {
        User user = repository.findById(id)
                              .orElse(null);

        if (user == null)
            return null;

        return mapper.mapDto(user);
    }

    public UserGetDto getUserByUsername(String username) {
        User user = repository.findByUsername(username)
                              .orElse(null);

        if (user == null)
            return null;

        return mapper.mapDto(user);
    }

    public String login(UserLoginDto loginDto) {
        User user = repository.findByUsername(loginDto.getUsername())
                              .orElse(null);

        String enteredPassword = Utils.encodePassword(loginDto.getPassword());

        if (user == null || !user.getPassword().equals(enteredPassword))
            return null;

        Instant issuedAt  = Instant.now();
        Instant expiresAt = LocalDateTime.now()
                                         .plusHours(1)
                                         .toInstant(ZoneId.systemDefault()
                                                          .getRules()
                                                          .getOffset(issuedAt)
                                         );

        return JWT.create()
                  .withIssuedAt(issuedAt)
                  .withExpiresAt(expiresAt)
                  .withSubject(user.getUsername())
                  .sign(algorithm);
    }

    public boolean isAuthorized(String token) {
        if (token == null || !token.startsWith("Bearer"))
            return false;

        String username = JWT.require(algorithm)
                             .build()
                             .verify(token.replace("Bearer ", ""))
                             .getSubject();

        return repository.findByUsername(username).isPresent();
    }

}
