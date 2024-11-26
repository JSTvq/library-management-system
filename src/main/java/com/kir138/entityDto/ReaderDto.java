package com.kir138.entityDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReaderDto {
    private String name;
    private String email;
}
