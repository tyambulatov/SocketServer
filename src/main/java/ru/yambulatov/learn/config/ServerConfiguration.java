package ru.yambulatov.learn.config;

import ru.yambulatov.learn.repository.impl.UserRepositoryImpl;
import ru.yambulatov.learn.request.processor.CompositeRequestProcessor;
import ru.yambulatov.learn.request.ExceptionHandlingProcessor;
import ru.yambulatov.learn.request.processor.ReqProcessor;
import ru.yambulatov.learn.request.processor.requestrule.PathRegexRule;
import ru.yambulatov.learn.request.processor.requestrule.PathStartsRule;
import ru.yambulatov.learn.request.processor.user.SingleUserRequestProcessor;
import ru.yambulatov.learn.request.processor.user.UsersRequestProcessor;
import ru.yambulatov.learn.response.processor.ResponseProcessor;
import ru.yambulatov.learn.response.processor.impl.ResponseProcessorImpl;
import ru.yambulatov.learn.service.UserService;
import ru.yambulatov.learn.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Map;

public class ServerConfiguration {
    private static final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    private static final List<String> allowedMimeTypes = List.of("*/*", "application/json", "text/html");

    private static final ResponseProcessor responseProcessor = new ResponseProcessorImpl(allowedMimeTypes);

    private static final ReqProcessor processor = new ExceptionHandlingProcessor(
            new CompositeRequestProcessor(
                    Map.of(
                            new PathStartsRule("/users"), new CompositeRequestProcessor(
                                    Map.of(
                                            new PathRegexRule("/users$"), new UsersRequestProcessor(
                                                    userService,
                                                    responseProcessor
                                            ),
                                            new PathRegexRule("/users/\\d+$"), new SingleUserRequestProcessor(
                                                    userService,
                                                    responseProcessor)
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
