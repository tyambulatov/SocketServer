package org.example.requestProcessor;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class BodyEncoder {

    private final List<String> acceptedTypes;

    public BodyEncoder(List<String> acceptedTypes) {
        this.acceptedTypes = acceptedTypes;
    }

    public byte[] buildBody(Object anything) {
        // ...
        return "{\"object\": \"object.toSting()\"}".getBytes(StandardCharsets.UTF_8);
    }
}
