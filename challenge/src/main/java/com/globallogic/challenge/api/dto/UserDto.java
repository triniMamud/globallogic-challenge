package com.globallogic.challenge.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=(.*\\d.*){2})(?=.*[a-z]).{8,12}$")
    private String password;

    private List<PhoneDto> phones;
}
