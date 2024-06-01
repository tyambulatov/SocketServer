package org.example.requestProcessor.user;

import java.util.List;

import jakarta.ws.rs.core.Response;
import org.example.exception.MethodNotAllowedException;
import org.example.exception.NoContentException;
import org.example.httpRequest.HttpRequest;
import org.example.model.User;
import org.example.requestProcessor.ReqProcessor;
import org.example.response.ResponseProcessor;
import org.example.service.UserService;

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
            throw new NoContentException();
        }
    }

    private void postRequest(HttpRequest httpRequest) {
//        if (httpRequest.hadBody()) {
//            UserForm userForm = userFormParsing(httpRequest.getBody());
//            usersService.addUser(userForm);
//        } else {
            // There is no body to create User.
            // Return response that no body was received.
//        }
    }


//    GET domain.com/my-api/users -> список юзеров
//    POST domain.com/my-api/users + body -> создать нового
//    GET domain.com/my-api/users/{userID} -> получить юзера
//    POST domain.com/my-api/users/{userId}/block + no body -> заблокировать юзера
}
