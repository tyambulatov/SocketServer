package ru.yambulatov.learn.request.processor;

import ru.yambulatov.learn.exception.NotFoundException;
import ru.yambulatov.learn.request.HttpRequest;
import ru.yambulatov.learn.request.processor.requestrule.RequestRule;

import java.util.Map;

public class CompositeRequestProcessor implements ReqProcessor {
    Map<RequestRule, ReqProcessor> processingRules;

    public CompositeRequestProcessor(Map<RequestRule, ReqProcessor> processingRules) {
        this.processingRules = processingRules;
    }

    @Override
    public void process(HttpRequest httpRequest) {
        ReqProcessor processor = processingRules.entrySet().stream()
                .filter(r -> r.getKey().matches(httpRequest))
                .findFirst()
                .orElseThrow(NotFoundException::new)
                .getValue();

        processor.process(httpRequest);
    }
}
