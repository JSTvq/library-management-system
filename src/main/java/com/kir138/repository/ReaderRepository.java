package com.kir138.repository;

import com.kir138.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long>, ReaderRepositoryCustom {
}
