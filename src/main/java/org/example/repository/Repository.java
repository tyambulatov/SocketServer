package org.example.repository;

import java.util.List;
import java.util.Optional;

public interface Repository <T, ID>{
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    T update(T entity);
    boolean deleteById(ID id);
}
