package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.model.Post;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.IPostRepository;

import java.util.List;

public class PostService {

    @Inject
    private IPostRepository    repository;
    @Inject
    private ICommentRepository commentRepository;

//    private final PostMapper postMapper;

    public List<Post> getAll() {
        return repository.findAll();
    }

    public Post getById(Long id) {
        return repository.findById(id)
                         .orElse(null);
    }

    public Post addPost(PostCreateDto createDto) {
        return repository.create(createDto);
    }

    public Post addComment(CommentCreateDto commentCreateDto) {
         Post post = repository.findById(commentCreateDto.getPostId())
                               .orElse(null);

         // TODO: Throw error
         if (post == null)
             return null;

         post.addComment(commentRepository.create(commentCreateDto));

         return post;
    }
    
}
