package rs.raf.student.filter;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import rs.raf.student.service.UserService;

import java.util.List;

@Provider
@PreMatching
public class AuthenticationFilter implements ContainerRequestFilter {

    private final List<String> nonAuthenticationPaths = List.of(
        "users"
    );

    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (!needsAuthentication(requestContext))
            return;

        if (!userService.isAuthorized(requestContext.getHeaderString("Authorization")))
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private boolean needsAuthentication(ContainerRequestContext requestContext) {
        return nonAuthenticationPaths.stream()
                                     .noneMatch(element -> requestContext.getUriInfo()
                                                                         .getPath()
                                                                         .contains(element));
    }

}
