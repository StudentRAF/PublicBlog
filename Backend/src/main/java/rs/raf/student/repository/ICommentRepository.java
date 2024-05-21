package rs.raf.student.repository;

import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentUpdateDto;
import rs.raf.student.model.Comment;

import java.util.Optional;

public interface ICommentRepository {

    Optional<Comment> findById(Long id);

    Optional<Comment> create(CommentCreateDto comment);

    Optional<Comment> update(CommentUpdateDto updateDto);

    Optional<Comment> deleteById(Long id);

}
