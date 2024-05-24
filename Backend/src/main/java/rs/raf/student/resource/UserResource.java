package rs.raf.student.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rs.raf.student.dto.user.UserLoginDto;
import rs.raf.student.service.UserService;

@Path("/users")
public class UserResource {

    @Inject
    private UserService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid UserLoginDto loginDto) {
        return Response.ok(service.getUserByUsername(loginDto.getUsername()))
                       .header("Authorization", service.login(loginDto))
                       .build();
    }

}
