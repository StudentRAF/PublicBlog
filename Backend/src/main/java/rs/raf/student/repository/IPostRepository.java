package rs.raf.student.repository;

import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.dto.post.PostUpdateDto;
import rs.raf.student.model.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {

    List<Post> findAll();

    Optional<Post> findById(Long id);

    Optional<Post> create(PostCreateDto createDto);

    Optional<Post> update(PostUpdateDto updateDto);

    Optional<Post> deleteById(Long id);

}
