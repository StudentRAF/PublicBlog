package rs.raf.student.filter;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;
import rs.raf.student.service.UserService;

@Provider
@PreMatching
public class ResponseFilter implements ContainerResponseFilter {

    @Inject
    private UserService userService;


    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
//        if (requestContext.getHeaderString("Authorization") == null);
    }

}
