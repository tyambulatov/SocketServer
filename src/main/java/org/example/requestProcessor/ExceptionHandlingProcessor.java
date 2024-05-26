package org.example.requestProcessor;

import java.io.IOException;
import java.util.Map;

import jakarta.ws.rs.core.Response;
import org.example.exception.NotFoundException;
import org.example.httpRequest.HttpRequest;
import org.example.response.UserResponse;

public class ExceptionHandlingProcessor implements ReqProcessor {
    ReqProcessor delete;

    public ExceptionHandlingProcessor(ReqProcessor delete) {
        this.delete = delete;
    }

    @Override
    public void process(HttpRequest httpRequest){
        try {
            delete.process(httpRequest);
        } catch (NotFoundException e) {
            new UserResponse(httpRequest).returnEmptyBody(Response.Status.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            new UserResponse(httpRequest).returnEmptyBody(Response.Status.BAD_REQUEST);
        }
    }
}
