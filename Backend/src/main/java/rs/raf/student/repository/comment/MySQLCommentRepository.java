package rs.raf.student.repository.comment;

import jakarta.inject.Inject;
import lombok.SneakyThrows;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentUpdateDto;
import rs.raf.student.mapper.CommentMapper;
import rs.raf.student.model.Comment;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.MySQLAbstractRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLCommentRepository extends MySQLAbstractRepository implements ICommentRepository {

    @Inject
    private CommentMapper mapper;

    @Override
    public Optional<Comment> findById(Long id) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      select *
                                                                      from comments
                                                                      where id = ?
                                                                      """);
            ResultSet resultSet         = executeById(statement, id)
        ) {
            if (resultSet.next())
                return Optional.of(new Comment(resultSet.getLong("id"),
                                               resultSet.getLong("post_id"),
                                               resultSet.getLong("author_id"),
                                               resultSet.getString("content"),
                                               resultSet.getDate("date").toLocalDate()));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        List<Comment> comments = new ArrayList<>();

        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      select *
                                                                      from comments
                                                                      where post_id = ?
                                                                      """);
            ResultSet  resultSet        = executeById(statement, postId)
        ) {
            while (resultSet.next())
                comments.add(new Comment(resultSet.getLong("id"),
                                   resultSet.getLong("post_id"),
                                   resultSet.getLong("author_id"),
                                   resultSet.getString("content"),
                                   resultSet.getDate("date").toLocalDate()));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return comments;
    }

    @Override
    public Optional<Comment> create(CommentCreateDto createDto) {
        Comment comment = new Comment();

        String[] generatedColumns = { "id" };

        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                          insert into comments(post_id, author_id, content, date)
                                                                          values (?, ?, ?, ?)
                                                                          """,
                                                                      generatedColumns);
            ResultSet resultSet         = executeCreatePost(statement, mapper.map(comment, createDto))
        ) {
            if (resultSet.next())
                comment.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.of(comment);
    }

    @Override
    public Optional<Comment> update(CommentUpdateDto updateDto) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      update comments
                                                                      set content = ?, date = ?
                                                                      where id = ?
                                                                      """)
        ) {
            executeUpdateComment(statement, updateDto);
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Comment> deleteById(Long id) {
        try(
            Connection connection       = createConnection();
            PreparedStatement statement = connection.prepareStatement("""
                                                                      delete from comments
                                                                      where id = ?
                                                                      """);
            ResultSet resultSet         = executeById(statement, id)
        ) {
            if (resultSet.next())
                return Optional.of(new Comment(resultSet.getLong("id"),
                                               resultSet.getLong("post_id"),
                                               resultSet.getLong("author_id"),
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
    private ResultSet executeCreatePost(PreparedStatement statement, Comment comment) {
        statement.setLong  (1, comment.getPostId());
        statement.setLong  (2, comment.getAuthorId());
        statement.setString(3, comment.getContent());
        statement.setDate  (4, Date.valueOf(comment.getDate()));

        statement.executeUpdate();

        return statement.getGeneratedKeys();
    }

    @SneakyThrows
    private void executeUpdateComment(PreparedStatement statement, CommentUpdateDto updateDto) {
        statement.setString(1, updateDto.getContent());
        statement.setDate  (2, Date.valueOf(LocalDate.now()));
        statement.setLong  (3, updateDto.getId());

        statement.executeUpdate();
    }

}
