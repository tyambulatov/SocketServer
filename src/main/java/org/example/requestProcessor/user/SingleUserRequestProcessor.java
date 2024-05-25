package org.example.requestProcessor.user;

import jakarta.ws.rs.core.Response;
import org.example.httpRequest.HttpRequest;
import org.example.model.User;
import org.example.requestProcessor.ReqProcessor;
import org.example.response.UserResponse;
import org.example.service.UsersService;
import org.example.service.impl.UsersServiceImpl;

import java.io.IOException;
import java.util.List;

public class SingleUserRequestProcessor implements ReqProcessor {
    UsersService usersService = new UsersServiceImpl();
    UserResponse userResponse;
    HttpRequest httpRequest;

    @Override
    public void process(HttpRequest httpRequest) throws IOException {
        this.httpRequest = httpRequest;
        this.userResponse = new UserResponse(httpRequest);

        switch (httpRequest.getMethod()) {
            case GET -> getRequest();
            default -> throw new IOException("Invalid http method name");
        }
    }

    private void getRequest() {
        Long id = Long.valueOf(httpRequest.getPath().split("/")[2]);
        User user = usersService.getUser(id);
        userResponse.returnEntities(Response.Status.OK, List.of(user));
    }
}
