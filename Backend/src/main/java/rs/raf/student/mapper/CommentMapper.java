package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.model.Comment;
import rs.raf.student.model.User;

public class CommentMapper {

    @Inject
    private UserMapper userMapper;

    public CommentGetDto toCommentDto(Comment comment, User user) {
        CommentGetDto commentGetDto = new CommentGetDto();

        commentGetDto.setAuthor(userMapper.toUserDto(user));
        commentGetDto.setContent(comment.getContent());
        commentGetDto.setDate(comment.getDate());

        return commentGetDto;
    }

}
