package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.utils.NotInUse;

@NotInUse(message = "The class is not currently in use, maybe in the future.")
public class CommentService {

    @Inject
    private ICommentRepository repository;

}
