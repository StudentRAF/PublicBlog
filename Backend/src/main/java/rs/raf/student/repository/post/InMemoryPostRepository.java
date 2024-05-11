package rs.raf.student.repository.post;

import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.model.Post;
import rs.raf.student.repository.IPostRepository;
import rs.raf.student.utils.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryPostRepository implements IPostRepository {

    private final List<Post> posts = new CopyOnWriteArrayList<>();

    @Override
    public List<Post> getAll() {
        return Utils.createList(posts.iterator());
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Utils.createStream(posts.iterator())
                    .filter(post -> post.getId().equals(id))
                    .findFirst();
    }

    @Override
    public Post create(PostCreateDto postCreateDto) {
        Post post = new Post();

        post.setAuthorId(postCreateDto.getAuthorId());
        post.setTitle(postCreateDto.getTitle());
        post.setContent(postCreateDto.getContent());
        post.setDate(LocalDate.now());

        synchronized (this) {
            post.setId((long) posts.size() + 1);
            posts.add(post);
        }

        return post;
    }

}
