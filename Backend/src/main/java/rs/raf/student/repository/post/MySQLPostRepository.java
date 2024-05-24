package rs.raf.student.repository.post;

import jakarta.inject.Inject;
import lombok.SneakyThrows;
import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.dto.post.PostUpdateDto;
import rs.raf.student.mapper.PostMapper;
import rs.raf.student.model.Post;
import rs.raf.student.repository.IPostRepository;
import rs.raf.student.repository.MySQLAbstractRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLPostRepository extends MySQLAbstractRepository implements IPostRepository {

    @Inject
    private PostMapper mapper;

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();

        try(
            Connection connection = createConnection();
            Statement  statement  = connection.createStatement();
            ResultSet  resultSet  = statement.executeQuery("""
                                                           select *
                                                           from posts
                                                           """)
        ) {
            while (resultSet.next())
                posts.add(new Post(resultSet.getLong("id"),
                                   resultSet.getLong("author_id"),
                                   resultSet.getString("title"),
                                   resultSet.getString("content"),
                                   resultSet.getDate("date").toLocalDate()));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return posts;
    }

    @Override
    public Optional<Post> findById(Long id) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      select *
                                                                      from posts
                                                                      where id = ?
                                                                      """);
            ResultSet resultSet         = executeById(statement, id)
        ) {
            if (resultSet.next())
                return Optional.of(new Post(resultSet.getLong("id"),
                                            resultSet.getLong("author_id"),
                                            resultSet.getString("title"),
                                            resultSet.getString("content"),
                                            resultSet.getDate("date").toLocalDate()));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Post> create(PostCreateDto createDto) {
        Post post = new Post();

        String[] generatedColumns = { "id" };

        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      insert into posts(author_id, title, content, date)
                                                                      values (?, ?, ?, ?)
                                                                      """,
                                                                      generatedColumns);
            ResultSet resultSet         = executeCreatePost(statement, mapper.map(post, createDto))
        ) {
            if (resultSet.next())
                post.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.of(post);
    }

    @Override
    public Optional<Post> update(PostUpdateDto updateDto) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      update posts
                                                                      set title = ?, content = ?, date = ?
                                                                      where id = ?
                                                                      """)
        ) {
            executeUpdatePost(statement, updateDto);
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Post> deleteById(Long id) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      delete from posts
                                                                      where id = ?
                                                                      """);
            ResultSet resultSet         = executeById(statement, id)
        ) {
            if (resultSet.next())
                return Optional.of(new Post(resultSet.getLong("id"),
                                            resultSet.getLong("author_id"),
                                            resultSet.getString("title"),
                                            resultSet.getString("content"),
                                            resultSet.getDate("date").toLocalDate()));
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
    private ResultSet executeCreatePost(PreparedStatement statement, Post post) {
        statement.setLong  (1, post.getAuthorId());
        statement.setString(2, post.getTitle());
        statement.setString(3, post.getContent());
        statement.setDate  (4, Date.valueOf(post.getDate()));

        statement.executeUpdate();

        return statement.getGeneratedKeys();
    }

    @SneakyThrows
    private void executeUpdatePost(PreparedStatement statement, PostUpdateDto updateDto) {
        statement.setString(1, updateDto.getTitle());
        statement.setString(2, updateDto.getContent());
        statement.setDate  (3, Date.valueOf(LocalDate.now()));
        statement.setLong  (4, updateDto.getId());

        statement.executeQuery();
    }

}
