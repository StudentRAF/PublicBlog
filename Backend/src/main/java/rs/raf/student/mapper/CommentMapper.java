package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.dto.comment.CommentUpdateDto;
import rs.raf.student.model.Comment;
import rs.raf.student.model.User;

import java.time.LocalDate;

public class CommentMapper {

    @Inject
    private UserMapper userMapper;

    public CommentGetDto mapDto(Comment comment, User user) {
        CommentGetDto commentGetDto = new CommentGetDto();

        commentGetDto.setAuthor(userMapper.mapDto(user));
        commentGetDto.setContent(comment.getContent());
        commentGetDto.setDate(comment.getDate());

        return commentGetDto;
    }

    public Comment map(Comment comment, CommentCreateDto createDto) {
        comment.setPostId(createDto.getPostId());
        comment.setAuthorId(createDto.getAuthorId());
        comment.setContent(createDto.getContent());
        comment.setDate(LocalDate.now());

        return comment;
    }

    public Comment map(Comment comment, CommentUpdateDto updateDto) {
        comment.setContent(updateDto.getContent());
        comment.setDate(LocalDate.now());

        return comment;
    }

}
