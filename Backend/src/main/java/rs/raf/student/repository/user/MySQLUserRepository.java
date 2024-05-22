package rs.raf.student.repository.user;

import jakarta.inject.Inject;
import lombok.SneakyThrows;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.MySQLAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class MySQLUserRepository extends MySQLAbstractRepository implements IUserRepository {

    @Inject
    private UserMapper userMapper;

    public MySQLUserRepository() {
        initialUsers.forEach(this::create);
    }

    @Override
    public Optional<User> findById(Long id) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("select * from users where id = ?");
            ResultSet resultSet         = executeQueryById(statement, id)
        ) {
            if (resultSet.next())
                return Optional.of(new User(resultSet.getLong("id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @SneakyThrows
    private ResultSet executeQueryById(PreparedStatement statement, Long id) {
        statement.setLong(1, id);

        return statement.executeQuery();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("select * from users where username = ?");
            ResultSet resultSet         = executeQueryByString(statement, username)
        ) {
            if (resultSet.next())
                return Optional.of(new User(resultSet.getLong("id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @SneakyThrows
    private ResultSet executeQueryByString(PreparedStatement statement, String string) {
        statement.setString(1, string);

        return statement.executeQuery();
    }

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
            ResultSet resultSet          = executeQueryCreateUser(statement, userMapper.map(user, createDto))
        ) {
            if (!resultSet.next())
                return Optional.empty();

            user.setId(resultSet.getLong("id"));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.of(user);
    }

    @Override
    public Optional<User> deleteById(Long id) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("delete from users where id = ?");
            ResultSet resultSet         = executeQueryById(statement, id)
        ) {
            if (resultSet.next())
                return Optional.of(new User(resultSet.getLong("id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @SneakyThrows
    private ResultSet executeQueryCreateUser(PreparedStatement statement, User user) {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPassword());

        return statement.executeQuery();
    }

    @Override
    public Optional<User> update(UserUpdateDto updateDto) {


        return Optional.empty();
    }

}
