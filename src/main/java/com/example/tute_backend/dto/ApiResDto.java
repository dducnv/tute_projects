package com.example.tute_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResDto<T> {
    private String message;
    private T data;
}
