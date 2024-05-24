package rs.raf.student.repository.user;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.MySQLAbstractRepository;
import rs.raf.student.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLUserRepository extends MySQLAbstractRepository implements IUserRepository {

    @Inject
    private UserMapper mapper;

    @PostConstruct
    private void initialise() {
        if (findById(1L).isEmpty())
            initialUsers.forEach(this::create);
    }

    @Override
    public Optional<User> findById(Long id) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      select * from users
                                                                      where id = ?
                                                                      """);
            ResultSet resultSet         = executeById(statement, id)
        ) {
            if (resultSet.next())
                return Optional.of(new User(resultSet.getLong("id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password"),
                                            resultSet.getBoolean("active")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      select * from users
                                                                      where username = ?
                                                                      """);
            ResultSet resultSet         = executeByString(statement, username)
        ) {
            if (resultSet.next())
                return Optional.of(new User(resultSet.getLong("id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password"),
                                            resultSet.getBoolean("active")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAllByPostId(Long postId) {
        List<User> users = new ArrayList<>();

        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      select users.id, first_name, last_name, username, password, active
                                                                      from comments
                                                                      join users on comments.author_id = users.id
                                                                      where post_id = ?
                                                                      """);
            ResultSet resultSet         = executeById(statement, postId)
        ) {
            while (resultSet.next())
                users.add(new User(resultSet.getLong("users.id"),
                                   resultSet.getString("first_name"),
                                   resultSet.getString("last_name"),
                                   resultSet.getString("username"),
                                   resultSet.getString("password"),
                                   resultSet.getBoolean("active")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return users;
    }

    @Override
    public Optional<User> create(UserCreateDto createDto) {
        User user = new User();

        String[] generatedColumns = { "id" };

        try(
            Connection        connection = createConnection();
            PreparedStatement statement  = connection.prepareStatement("""
                                                                       insert into users(first_name, last_name, username, password)
                                                                       values (?, ?, ?, ?)
                                                                       """,
                                                                      generatedColumns);
            ResultSet resultSet          = executeCreateUser(statement, mapper.map(user, createDto))
        ) {
            if (resultSet.next())
                user.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.of(user);
    }

    @Override
    public Optional<User> update(UserUpdateDto updateDto) {
        try(
                Connection connection       = createConnection();
                PreparedStatement statement = connection.prepareStatement("""
                                                                          update users
                                                                          set first_name = ?, last_name = ?, username = ?, password = ?
                                                                          where id = ?
                                                                          """)
        ) {
            executeUpdateUser(statement, updateDto);
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> deleteById(Long id) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      update users
                                                                      set active = false
                                                                      where id = ?
                                                                      """)
        ) {
            executeDeleteUser(statement, id);
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @SneakyThrows
    private ResultSet executeById(PreparedStatement statement, Long id) {
        statement.setLong(1, id);

        return statement.executeQuery();
    }

    @SneakyThrows
    private ResultSet executeByString(PreparedStatement statement, String string) {
        statement.setString(1, string);

        return statement.executeQuery();
    }

    @SneakyThrows
    private ResultSet executeCreateUser(PreparedStatement statement, User user) {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPassword());

        statement.executeUpdate();

        return statement.getGeneratedKeys();
    }

    @SneakyThrows
    private void executeUpdateUser(PreparedStatement statement, UserUpdateDto updateDto) {
        statement.setString(1, updateDto.getFirstName());
        statement.setString(2, updateDto.getLastName());
        statement.setString(3, updateDto.getUsername());
        statement.setString(4, Utils.encodePassword(updateDto.getPassword()));
        statement.setLong  (5, updateDto.getId());

        statement.executeUpdate();
    }

    @SneakyThrows
    private void executeDeleteUser(PreparedStatement statement, Long id) {
        statement.setLong(1, id);

        statement.executeUpdate();
    }

}
