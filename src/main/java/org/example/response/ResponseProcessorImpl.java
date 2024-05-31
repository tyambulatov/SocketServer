package org.example.response;

import jakarta.ws.rs.core.Response;
import org.example.httpRequest.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ResponseProcessorImpl implements ResponseProcessor {

    private final List<String> allowedMimeTypes;

    public ResponseProcessorImpl(List<String> allowedMimeTypes) {
        this.allowedMimeTypes = allowedMimeTypes;
    }

    @Override
    public void returnBody(HttpRequest httpRequest, Response.Status status, Object body) {
        HttpResponse httpResponse = new HttpResponse(allowedMimeTypes, httpRequest, status, body);
        writeResponse(httpRequest, httpResponse.toByteArray());
    }

    @Override
    public void returnEmptyBody(HttpRequest httpRequest, Response.Status status) {
        final Object errorBody = "Error";
        HttpResponse httpResponse = new HttpResponse(allowedMimeTypes, httpRequest, status, errorBody);
        writeResponse(httpRequest, httpResponse.toByteArray());
    }

    private void writeResponse(HttpRequest httpRequest, byte[] response) {
        try {
            OutputStream outputStream = httpRequest.getSocket().getOutputStream();
            outputStream.write(response);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
