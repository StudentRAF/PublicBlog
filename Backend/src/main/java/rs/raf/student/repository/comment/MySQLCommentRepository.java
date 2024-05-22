package rs.raf.student.repository.comment;

import lombok.SneakyThrows;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentUpdateDto;
import rs.raf.student.model.Comment;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.MySQLAbstractRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class MySQLCommentRepository extends MySQLAbstractRepository implements ICommentRepository {

    private final List<Comment> comments = new CopyOnWriteArrayList<>();

    @Override
    public Optional<Comment> findById(Long id) {
        try(
                Connection connection       = createConnection();
                PreparedStatement statement = connection.prepareStatement("select * from users where id = ?");
                ResultSet resultSet         = executeQueryById(statement, id)
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
    private ResultSet executeQueryById(PreparedStatement statement, Long id) {
        statement.setLong(1, id);

        return statement.executeQuery();
    }

    @Override
    public Optional<Comment> create(CommentCreateDto createDto) {
        Comment comment = new Comment();

        comment.setAuthorId(createDto.getAuthorId());
        comment.setContent(createDto.getContent());
        comment.setDate(LocalDate.now());

        String[] generatedColumns = { "id" };

        try(
                Connection connection       = createConnection();
                PreparedStatement statement = connection.prepareStatement("""
                                                                          insert into comments(author_id, content, date)
                                                                          values (?, ?, ?)
                                                                          """,
                                                                          generatedColumns);
                ResultSet resultSet         = executeQueryCreatePost(statement, comment)
        ) {
            if (!resultSet.next())
                return Optional.empty();

            comment.setId(resultSet.getLong("id"));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

//        try(
//                Connection connection       = createConnection();
//                PreparedStatement statement = connection.prepareStatement("""
//                                                                          insert into post_comments(post_id, comment_id)
//                                                                          values (?, ?)
//                                                                          """,
//                                                                          generatedColumns);
//                ResultSet resultSet         = executeQueryCreatePost(statement, comment)
//        ) {
//            if (!resultSet.next()) {
//                return Optional.empty();
//            }
//                return Optional.empty();
//
//            comment.setId(resultSet.getLong("id"));
//        }
//        catch (Exception exception) {
//            exception.printStackTrace(System.err);
//        }

        return Optional.of(comment);
    }

    @Override
    public Optional<Comment> update(CommentUpdateDto updateDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Comment> deleteById(Long id) {
        return Optional.empty();
    }

    @SneakyThrows
    private ResultSet executeQueryCreatePost(PreparedStatement statement, Comment comment) {
        statement.setLong  (1, comment.getAuthorId());
        statement.setString(2, comment.getContent());
        statement.setDate  (4, Date.valueOf(comment.getDate()));

        return statement.executeQuery();
    }


}
