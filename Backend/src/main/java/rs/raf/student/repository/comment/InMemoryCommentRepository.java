package rs.raf.student.repository.comment;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentUpdateDto;
import rs.raf.student.mapper.CommentMapper;
import rs.raf.student.model.Comment;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryCommentRepository implements ICommentRepository {

    private final List<Comment> comments = new CopyOnWriteArrayList<>();

    @Inject
    private CommentMapper commentMapper;

    @Override
    public Optional<Comment> findById(Long id) {
        return Utils.createStream(comments.iterator())
                    .filter(comment -> comment.getId().equals(id))
                    .findFirst();
    }

    @Override
    public Optional<Comment> create(CommentCreateDto createDto) {
        Comment comment = new Comment();

        synchronized (this) {
            comment.setId((long) comments.size() + 1);
            comments.add(comment);
        }

        return Optional.of(commentMapper.map(comment, createDto));
    }

    @Override
    public Optional<Comment> update(CommentUpdateDto updateDto) {
        return Utils.createStream(comments.iterator())
                    .filter((item -> item.getId().equals(updateDto.getId())))
                    .map(item -> commentMapper.map(item, updateDto))
                    .findFirst();
    }

    @Override
    public Optional<Comment> deleteById(Long id) {
        Comment comment = Utils.createStream(comments.iterator())
                               .filter((item -> item.getId().equals(id)))
                               .findFirst()
                               .orElse(null);

        if (comment != null)
            comments.remove(comment);

        return Optional.ofNullable(comment);
    }

}
