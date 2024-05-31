package org.example.requestProcessor;

import jakarta.ws.rs.core.Response;
import org.example.exception.NotFoundException;
import org.example.httpRequest.HttpRequest;
import org.example.response.ResponseProcessorImpl;

import java.util.List;
import java.util.NoSuchElementException;

public class ExceptionHandlingProcessor implements ReqProcessor {
    private final ReqProcessor reqProcessor;
    private final List<String> allowedMimeTypes;

    public ExceptionHandlingProcessor(ReqProcessor reqProcessor, List<String> allowedMimeTypes) {
        this.reqProcessor = reqProcessor;
        this.allowedMimeTypes = allowedMimeTypes;
    }

    @Override
    public void process(HttpRequest httpRequest){
        try {
            reqProcessor.process(httpRequest);
        } catch (NotFoundException e) {
            new ResponseProcessorImpl(allowedMimeTypes).returnEmptyBody(httpRequest, Response.Status.NOT_FOUND);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            new ResponseProcessorImpl(allowedMimeTypes).returnEmptyBody(httpRequest, Response.Status.BAD_REQUEST);
        }
    }
}
