package com.kir138.mapper;

import com.kir138.model.entity.Reader;
import com.kir138.model.dto.ReaderDto;

public class ReaderMapper {
    public ReaderDto toReader(Reader reader) {
        return ReaderDto.builder()
                .email(reader.getEmail())
                .name(reader.getName())
                .build();
    }
}
