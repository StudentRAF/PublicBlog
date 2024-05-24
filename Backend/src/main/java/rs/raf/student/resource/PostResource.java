package rs.raf.student.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.post.PostCreateDto;
import rs.raf.student.dto.post.PostGetDto;
import rs.raf.student.service.PostService;

import java.util.List;

@Path("/posts")
public class PostResource {

    @Inject
    private PostService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PostGetDto> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PostGetDto getById(@PathParam("id") Long id) {
        return service.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PostGetDto addPost(@Valid PostCreateDto createDto) {
        return service.addPost(createDto);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PostGetDto addComment(@Valid CommentCreateDto createDto) {
        return service.addComment(createDto);
    }

}
