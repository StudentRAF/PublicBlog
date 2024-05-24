package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.dto.post.PostGetDto;
import rs.raf.student.mapper.PostMapper;
import rs.raf.student.model.Comment;
import rs.raf.student.model.Post;
import rs.raf.student.model.User;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.IPostRepository;
import rs.raf.student.repository.IUserRepository;

import java.util.List;

public class PostService {

    @Inject
    private IPostRepository    repository;
    @Inject
    private IUserRepository    userRepository;
    @Inject
    private ICommentRepository commentRepository;
    @Inject
    private PostMapper         mapper;

    public List<PostGetDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(this::toDto)
                         .toList();
    }

    public PostGetDto getById(Long id) {
        return toDto(repository.findById(id)
                               .orElse(null));
    }

    public PostGetDto addPost(PostCreateDto createDto) {
        return toDto(repository.create(createDto)
                               .orElse(null));
    }

    public PostGetDto addComment(CommentCreateDto commentCreateDto) {
         Post post = repository.findById(commentCreateDto.getPostId())
                               .orElse(null);

         if (post == null) //TODO: Throw error
             return null;

         Comment comment = commentRepository.create(commentCreateDto)
                                            .orElse(null);

         if (comment == null) //TODO: Throw error
             return null;


         return toDto(post);
    }

    private PostGetDto toDto(Post post) {
        if (post == null) //TODO: Throw error
            return null;

        User user = userRepository.findById(post.getAuthorId())
                                  .orElse(null);

        if (user == null) //TODO: Throw error
            return null;

        List<Comment> comments   = commentRepository.findAllByPostId(post.getId());
        List<User>    commenters = userRepository.findAllByPostId(post.getId());

        if (comments.size() != commenters.size())
            return null; //TODO: Throw error

        return mapper.mapDto(post, user, comments, commenters);
    }
    
}
