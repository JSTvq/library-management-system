package com.kir138.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SimpleCrudRepository<T, K> implements JpaRepository<T, K> {

    @Override
    public void flush() {

    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<T> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<K> ks) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public T getOne(K k) {
        return null;
    }

    @Override
    public T getById(K k) {
        return null;
    }

    @Override
    public T getReferenceById(K k) {
        return null;
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends T> S save(S entity) {
        return null;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<T> findById(K k) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(K k) {
        return false;
    }

    @Override
    public List<T> findAll() {
        return List.of();
    }

    @Override
    public List<T> findAllById(Iterable<K> ks) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(K k) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends K> ks) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<T> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }
}