package rs.raf.student.repository.comment;

import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.model.Comment;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.utils.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryCommentRepository implements ICommentRepository {

    private final List<Comment> comments = new CopyOnWriteArrayList<>();

    @Override
    public Comment findById(Long id) {
        return Utils.createStream(comments.iterator())
                    .filter(comment -> comment.getId().equals(id))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public Comment create(CommentCreateDto createDto) {
        Comment comment = new Comment();

        comment.setAuthorId(createDto.getAuthorId());
        comment.setContent(createDto.getContent());
        comment.setDate(LocalDate.now());

        synchronized (this) {
            comment.setId((long) comments.size() + 1);
            comments.add(comment);
        }

        return comment;
    }

}
