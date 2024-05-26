package org.example.response;

import jakarta.ws.rs.core.Response;

import java.util.List;

public interface ResponseProcessor<T> {
    void returnEntities(Response.Status status, List<T> entities);

    default void returnEmptyBody(Response.Status status) {
        // TODO
    };

    void saveEntity(T entity);
}
