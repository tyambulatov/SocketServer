package org.example.requestProcessor.user;

import org.example.httpRequest.HttpRequest;
import org.example.response.OldResponseProcessor;

import java.io.IOException;

public class OldRequestProcessor {
    HttpRequest httpRequest;
    OldResponseProcessor oldResponseProcessor;

    public OldRequestProcessor(HttpRequest httpRequest) throws IOException {
        this.httpRequest = httpRequest;
        oldResponseProcessor = new OldResponseProcessor(httpRequest);
    }

    public void process() throws IOException {
        switch (httpRequest.getMethod()) {
            case GET -> getRequest();
            case PUT -> putRequest();
            case POST -> postRequest();
            case DELETE -> deleteRequest();
            default -> throw new IllegalArgumentException("Invalid http method name");
        }

        // path
        // /users - GET all users
        // /users/123 - GET user id=123

        // /users -> GET / POST

        // GET
        // /users, /products, /transactions

//    GET domain.com/my-api/users -> список юзеров
//    POST domain.com/my-api/users + body -> создать нового
//    GET domain.com/my-api/users/{userID} -> получить юзера
//    POST domain.com/my-api/users/{userId}/block + no body -> заблокировать юзера

    }

    private void getRequest() throws IOException {
        oldResponseProcessor.getResponse();
    }

    private void putRequest() throws IOException {
        oldResponseProcessor.putResponse();
    }

    private void postRequest() throws IOException {
        oldResponseProcessor.postResponse();
    }


    private void deleteRequest() throws IOException {
        oldResponseProcessor.deleteResponse();
    }
}
