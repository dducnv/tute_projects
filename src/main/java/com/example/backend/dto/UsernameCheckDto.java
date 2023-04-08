package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsernameCheckDto {
    @NotNull(message = "Username không được để trống.")
    @NotBlank(message = "Username không được để trống.")
    private String username;
}
