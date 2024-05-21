package rs.raf.student.repository.post;

import lombok.SneakyThrows;
import rs.raf.student.dto.post.PostCreateDto;
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

    @Override
    @SneakyThrows
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();

        try(
                Connection connection = createConnection();
                Statement  statement  = connection.createStatement();
                ResultSet  resultSet  = statement.executeQuery("select * from posts")
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
                PreparedStatement statement = connection.prepareStatement("select * from posts where id = ?");
                ResultSet resultSet         = executeQueryById(statement, id)
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
    private ResultSet executeQueryById(PreparedStatement statement, Long id) {
        statement.setLong(1, id);

        return statement.executeQuery();
    }

    @Override
    public Post create(PostCreateDto postCreateDto) {
        Post post = new Post();

        post.setAuthorId(postCreateDto.getAuthorId());
        post.setTitle(postCreateDto.getTitle());
        post.setContent(postCreateDto.getContent());
        post.setDate(LocalDate.now());

        String[] generatedColumns = { "id" };

        try(
                Connection connection       = createConnection();
                PreparedStatement statement = connection.prepareStatement("""
                                                                          insert into posts(author_id, title, content, date)
                                                                          values (?, ?, ?, ?)
                                                                          """,
                                                                          generatedColumns);
                ResultSet resultSet         = executeQueryCreatePost(statement, post)
        ) {
            if (!resultSet.next())
                return null;

            post.setId(resultSet.getLong("id"));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return post;
    }

    @SneakyThrows
    private ResultSet executeQueryCreatePost(PreparedStatement statement, Post post) {
        statement.setLong  (1, post.getAuthorId());
        statement.setString(2, post.getTitle());
        statement.setString(3, post.getContent());
        statement.setDate  (4, Date.valueOf(post.getDate()));

        return statement.executeQuery();
    }

}