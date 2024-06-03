package ru.yambulatov.learn.request.processor.user;

import jakarta.ws.rs.core.Response;
import ru.yambulatov.learn.request.HttpRequest;
import ru.yambulatov.learn.model.User;
import ru.yambulatov.learn.request.processor.ReqProcessor;
import ru.yambulatov.learn.response.processor.ResponseProcessor;
import ru.yambulatov.learn.service.UserService;

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

}
