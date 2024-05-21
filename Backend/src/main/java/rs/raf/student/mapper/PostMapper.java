package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.dto.post.PostGetDto;
import rs.raf.student.dto.post.PostUpdateDto;
import rs.raf.student.model.Comment;
import rs.raf.student.model.Post;
import rs.raf.student.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public class PostMapper {

    @Inject
    private UserMapper userMapper;

    @Inject
    private CommentMapper commentMapper;

    public PostGetDto mapDto(Post post, User user, List<Comment> comments, List<User> commenters) {
        PostGetDto getDto = new PostGetDto();

        getDto.setId(post.getId());
        getDto.setTitle(post.getTitle());
        getDto.setContent(post.getContent());
        getDto.setDate(post.getDate());
        getDto.setAuthor(userMapper.mapDto(user));
        getDto.setComments(
                IntStream.range(0, comments.size())
                         .mapToObj(index -> commentMapper.mapDto(comments.get(index), commenters.get(index)))
                         .toList()
        );

        return getDto;
    }

    public Post map(Post post, PostCreateDto createDto) {
        post.setTitle(createDto.getTitle());
        post.setContent(createDto.getContent());
        post.setAuthorId(createDto.getAuthorId());
        post.setDate(LocalDate.now());

        return post;
    }

    public Post map(Post post, PostUpdateDto updateDto) {
        post.setTitle(updateDto.getTitle());
        post.setContent(updateDto.getContent());

        return post;
    }

}
