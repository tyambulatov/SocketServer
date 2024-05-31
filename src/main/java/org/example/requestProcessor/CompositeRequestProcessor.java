package org.example.requestProcessor;

import org.example.httpRequest.HttpRequest;
import org.example.requestProcessor.requestRule.RequestRule;

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
                .orElseThrow()
                .getValue();

        processor.process(httpRequest);
    }
}
