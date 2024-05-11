package rs.raf.student.repository;

import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.model.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {

    List<Post> getAll();

    Optional<Post> findById(Long id);

    Post create(PostCreateDto postCreateDto);

}
