package org.example.requestProcessor.user;

import jakarta.ws.rs.core.Response;
import org.example.httpRequest.HttpRequest;
import org.example.model.User;
import org.example.requestProcessor.ReqProcessor;
import org.example.response.ResponseProcessor;
import org.example.service.UserService;

import java.util.List;
import java.util.Objects;

public class SingleUserRequestProcessor implements ReqProcessor {

    private final UserService usersService;
    private final ResponseProcessor responseProcessor;

    public SingleUserRequestProcessor(UserService usersService, ResponseProcessor responseProcessor) {
        this.usersService = usersService;
        this.responseProcessor = responseProcessor;
    }

    @Override
    public void process(HttpRequest httpRequest) {
        if (Objects.requireNonNull(httpRequest.getMethod()) == HttpRequest.Method.GET) {
            getRequest(httpRequest);
        } else {
            throw new IllegalArgumentException("Invalid request");
        }
    }

    private void getRequest(HttpRequest httpRequest) {
        Long id = Long.valueOf(httpRequest.getPath().split("/")[2]);
        User user = usersService.getUser(id);
        responseProcessor.returnBody(httpRequest, Response.Status.OK, List.of(user));
    }

//    users/123/cart/1

}
