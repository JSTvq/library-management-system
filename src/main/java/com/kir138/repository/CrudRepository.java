package com.kir138.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K>{
    T save(T t);
    List<T> findAll();
    Optional<T> findById(K id);
    void deleteById(K id);
}
