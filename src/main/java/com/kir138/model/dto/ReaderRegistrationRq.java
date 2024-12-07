package com.kir138.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReaderRegistrationRq {
    private String name;
    private String email;
}
