package ru.yambulatov.learn.request;

import jakarta.ws.rs.core.Response;
import ru.yambulatov.learn.exception.*;
import ru.yambulatov.learn.request.processor.ReqProcessor;
import ru.yambulatov.learn.response.processor.impl.ResponseProcessorImpl;

import java.util.List;

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
        } catch (BadRequestException e) {
            errorResponse(httpRequest, Response.Status.BAD_REQUEST);
        } catch (NotFoundException e) {
            errorResponse(httpRequest, Response.Status.NOT_FOUND);
        } catch (LengthRequiredException e) {
            errorResponse(httpRequest, Response.Status.LENGTH_REQUIRED);
        } catch (MethodNotAllowedException e) {
            errorResponse(httpRequest, Response.Status.METHOD_NOT_ALLOWED);
        }
    }

    private void errorResponse(HttpRequest httpRequest, Response.Status status) {
        new ResponseProcessorImpl(allowedMimeTypes).returnNoBody(httpRequest, status);
    }
}
