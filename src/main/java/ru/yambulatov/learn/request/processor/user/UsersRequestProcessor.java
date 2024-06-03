package ru.yambulatov.learn.request.processor.user;

import jakarta.ws.rs.core.Response;
import ru.yambulatov.learn.exception.MethodNotAllowedException;
import ru.yambulatov.learn.request.HttpRequest;
import ru.yambulatov.learn.model.User;
import ru.yambulatov.learn.request.processor.ReqProcessor;
import ru.yambulatov.learn.response.processor.ResponseProcessor;
import ru.yambulatov.learn.service.UserService;

import java.util.List;

public class UsersRequestProcessor implements ReqProcessor {

    private final UserService userService;
    private final ResponseProcessor responseProcessor;

    public UsersRequestProcessor(UserService userService, ResponseProcessor responseProcessor) {
        this.userService = userService;
        this.responseProcessor = responseProcessor;
    }

    @Override
    public void process(HttpRequest httpRequest) {
        switch (httpRequest.getMethod()) {
            case GET -> getRequest(httpRequest);
            case POST -> postRequest(httpRequest);
            default -> throw new MethodNotAllowedException();
        }
    }

    private void getRequest(HttpRequest httpRequest) {
        List<User> userList = userService.getAllUsers();

        if (!userList.isEmpty()) {
            responseProcessor.returnBody(httpRequest, Response.Status.OK, userList);
        } else {
            responseProcessor.returnNoBody(httpRequest, Response.Status.NO_CONTENT);
        }
    }

    private void postRequest(HttpRequest httpRequest) {
        // TODO create implementation
//        if (httpRequest.hadBody()) {
//            UserForm userForm = userFormParsing(httpRequest.getBody());
//            usersService.addUser(userForm);
//        } else {
//             There is no body to create User.
//             Return response that no body was received.
//        }
    }

//    GET domain.com/my-api/users -> список юзеров
//    POST domain.com/my-api/users + body -> создать нового
//    GET domain.com/my-api/users/{userID} -> получить юзера
//    POST domain.com/my-api/users/{userId}/block + no body -> заблокировать юзера
}
