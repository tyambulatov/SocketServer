package org.example.requestProcessor.user;

import java.io.IOException;
import java.util.List;

import jakarta.ws.rs.core.Response;
import org.example.httpRequest.HttpRequest;
import org.example.model.User;
import org.example.requestProcessor.ReqProcessor;
import org.example.response.UserResponse;
import org.example.service.UsersService;
import org.example.service.impl.UsersServiceImpl;

public class UsersRequestProcessor implements ReqProcessor {
    UsersService usersService = new UsersServiceImpl();
    // TODO: выпилить поля
    UserResponse userResponse;
    HttpRequest httpRequest;

    @Override
    public void process(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        this.userResponse = new UserResponse(httpRequest);

        switch (httpRequest.getMethod()) {
            case GET -> getRequest();
            case POST -> postRequest();
            default -> throw new IllegalArgumentException("Incorrect user request");
        }
    }

    private void getRequest() {
        List<User> userList = usersService.getAllUsers();
        if (!userList.isEmpty()) {
            userResponse.returnEntities(Response.Status.OK, userList);
        } else {
            userResponse.returnEmptyBody(Response.Status.NO_CONTENT);
        }
    }

    private void postRequest() {
        if (httpRequest.haveBody()) {
//            UserForm userForm = userFormParsing(httpRequest.getBody());
//            usersService.addUser(userForm);
        } else {
            // There is no body to create User.
            // Return response that no body was received.
        }
    }
}
