package rs.raf.student.repository;

import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.model.Comment;

public interface ICommentRepository {

    Comment findById(Long id);

    Comment create(CommentCreateDto comment);

}
