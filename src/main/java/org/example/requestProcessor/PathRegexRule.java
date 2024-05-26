package org.example.requestProcessor;

import java.util.regex.Pattern;

import org.example.httpRequest.HttpRequest;

public class PathRegexRule implements RequestRule {

    private final Pattern pattern;

    public PathRegexRule(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }
    public PathRegexRule(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matches(HttpRequest request) {
        return pattern.matcher(request.getPath()).matches();
    }
}
