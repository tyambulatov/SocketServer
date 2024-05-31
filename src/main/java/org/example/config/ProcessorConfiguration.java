package org.example.config;

import org.example.repository.impl.UserRepositoryImpl;
import org.example.requestProcessor.CompositeRequestProcessor;
import org.example.requestProcessor.ExceptionHandlingProcessor;
import org.example.requestProcessor.ReqProcessor;
import org.example.requestProcessor.requestRule.PathRegexRule;
import org.example.requestProcessor.requestRule.PathStartsRule;
import org.example.requestProcessor.user.SingleUserRequestProcessor;
import org.example.requestProcessor.user.UsersRequestProcessor;
import org.example.response.ResponseProcessor;
import org.example.response.ResponseProcessorImpl;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Map;

public class ProcessorConfiguration {
    private static final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    private static final List<String> allowedMimeTypes = List.of("*/*", "application/json", "text/html");

    private static final ResponseProcessor responseProcessor = new ResponseProcessorImpl(allowedMimeTypes);

    private static final ReqProcessor processor = new ExceptionHandlingProcessor(
            new CompositeRequestProcessor(
                    Map.of(
                            new PathStartsRule("/users"), new CompositeRequestProcessor(
                                    Map.of(
                                            new PathRegexRule("/users/\\d+$"), new SingleUserRequestProcessor(
                                                    userService,
                                                    responseProcessor),
                                            new PathRegexRule("/users$"), new UsersRequestProcessor(
                                                    userService,
                                                    responseProcessor
                                            )
                                    )
                            ),
                            new PathStartsRule("/products"), new CompositeRequestProcessor(Map.of())
                    )
            ),
            allowedMimeTypes
    );

    public static ReqProcessor getProcessor() {
        return processor;
    }
}
