package rs.raf.student.repository;

import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentUpdateDto;
import rs.raf.student.model.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentRepository {

    Optional<Comment> findById(Long id);

    List<Comment> findAllByPostId(Long postId);

    Optional<Comment> create(CommentCreateDto comment);

    Optional<Comment> update(CommentUpdateDto updateDto);

    Optional<Comment> deleteById(Long id);

}
