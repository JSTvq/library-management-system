package com.kir138.mapper;

import com.kir138.entity.Reader;
import com.kir138.entityDto.ReaderDto;

public class ReaderMapper {
    public ReaderDto toReader(Reader reader) {
        return ReaderDto.builder()
                .email(reader.getEmail())
                .name(reader.getName())
                .build();
    }
}
