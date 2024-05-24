package rs.raf.student.repository.post;

import jakarta.inject.Inject;
import lombok.Getter;
import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.dto.post.PostUpdateDto;
import rs.raf.student.mapper.PostMapper;
import rs.raf.student.model.Post;
import rs.raf.student.repository.IPostRepository;
import rs.raf.student.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class InMemoryPostRepository implements IPostRepository {

    private final List<Post> posts = new CopyOnWriteArrayList<>();

    @Inject
    private PostMapper mapper;

    @Override
    public List<Post> findAll() {
        return Utils.createList(posts.iterator());
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Utils.createStream(posts.iterator())
                    .filter(post -> post.getId().equals(id))
                    .findFirst();
    }

    @Override
    public Optional<Post> create(PostCreateDto createDto) {
        Post post = new Post();

        synchronized (this) {
            post.setId((long) posts.size() + 1);
            posts.add(post);
        }

        return Optional.of(mapper.map(post, createDto));
    }

    @Override
    public Optional<Post> update(PostUpdateDto updateDto) {
        return Utils.createStream(posts.iterator())
                    .filter((item -> item.getId().equals(updateDto.getId())))
                    .map(item -> mapper.map(item, updateDto))
                    .findFirst();
    }

    @Override
    public Optional<Post> deleteById(Long id) {
        Post post = Utils.createStream(posts.iterator())
                         .filter((item -> item.getId().equals(id)))
                         .findFirst()
                         .orElse(null);

        if (post != null)
            posts.remove(post);

        return Optional.ofNullable(post);
    }

}
