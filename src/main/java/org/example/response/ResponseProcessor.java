package org.example.response;

import jakarta.ws.rs.core.Response;

import java.util.List;

public interface ResponseProcessor<T> {
    public void returnEntities(Response.Status status, List<T> entities);
    public void saveEntity(T entity);
}
