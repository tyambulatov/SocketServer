package org.example.response;

import jakarta.ws.rs.core.Response;
import org.example.httpRequest.HttpRequest;
import org.example.model.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class UserResponse implements ResponseProcessor<User> {
    BufferedWriter bufferedWriter;
    HttpRequest httpRequest;

    public UserResponse(HttpRequest httpRequest) {
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpRequest.getSocket().getOutputStream()));
            this.httpRequest = httpRequest;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void returnEntities(Response.Status statusCode, List<User> entities) {
        try {
            bufferedWriter.write(buildResponse(statusCode, entities));
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveEntity(User entity) {

    }

    private String buildResponse(Response.Status statusCode, List<User> entities) {
        StringBuilder response = new StringBuilder();
        response.append(statusLineBuilder(statusCode));
        response.append(headersBuilder());
        response.append("\r\n");
        response.append(bodyBuilder(entities));
        return response.toString();
    }

    private String statusLineBuilder(Response.Status statusCode) {
        return httpRequest.getProtocol() + " " + statusCode.getStatusCode() + " " + statusCode.toString() + "\r\n";
    }

    /**
     * Creates string of response headers
     * @return name of first accept type
     */
    private String headersBuilder() {
        return httpRequest.getAcceptTypes().get(0) + "\r\n";
    }
    //TODO add different representations of body depending on type

    private String bodyBuilder(List<User> entities) {
        String bodyType = httpRequest.getAcceptTypes().get(0);
        return entities.toString();
    }
}
