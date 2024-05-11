package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.post.PostGetDto;
import rs.raf.student.model.Post;
import rs.raf.student.model.User;

import java.util.List;
import java.util.stream.IntStream;

public class PostMapper {

    @Inject
    private UserMapper userMapper;

    @Inject
    private CommentMapper commentMapper;

    public PostGetDto toPostGetDto(Post post, User user, List<User> commenters) {
        PostGetDto getDto = new PostGetDto();

        getDto.setId(post.getId());
        getDto.setTitle(post.getTitle());
        getDto.setContent(post.getContent());
        getDto.setDate(post.getDate());
        getDto.setAuthor(userMapper.toUserDto(user));
        getDto.setComments(
                IntStream.range(0, post.getComments().size())
                         .mapToObj(index -> commentMapper.toCommentDto(post.getComments().get(index), commenters.get(index)))
                         .toList()
        );

        return getDto;
    }

}
