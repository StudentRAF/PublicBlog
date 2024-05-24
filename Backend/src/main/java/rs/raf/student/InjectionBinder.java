package rs.raf.student;

import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import rs.raf.student.mapper.CommentMapper;
import rs.raf.student.mapper.PostMapper;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.IPostRepository;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.comment.MySQLCommentRepository;
import rs.raf.student.repository.post.MySQLPostRepository;
import rs.raf.student.repository.user.MySQLUserRepository;
import rs.raf.student.service.CommentService;
import rs.raf.student.service.PostService;
import rs.raf.student.service.UserService;

public class InjectionBinder extends AbstractBinder {

    @Override
    protected void configure() {
//        bind(InMemoryUserRepository.class).to(IUserRepository.class).in(Singleton.class);
//        bind(InMemoryCommentRepository.class).to(ICommentRepository.class).in(Singleton.class);
//        bind(InMemoryPostRepository.class).to(IPostRepository.class).in(Singleton.class);

        bind(MySQLUserRepository.class).to(IUserRepository.class).in(Singleton.class);
        bind(MySQLCommentRepository.class).to(ICommentRepository.class).in(Singleton.class);
        bind(MySQLPostRepository.class).to(IPostRepository.class).in(Singleton.class);

        bindAsContract(UserService.class);
        bindAsContract(CommentService.class);
        bindAsContract(PostService.class);

        bindAsContract(UserMapper.class);
        bindAsContract(CommentMapper.class);
        bindAsContract(PostMapper.class);
    }

}
