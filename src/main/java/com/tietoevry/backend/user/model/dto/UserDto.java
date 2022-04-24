package com.tietoevry.backend.user.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.context.annotation.Bean;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Setter
@Getter
@Valid
public class UserDto {
    Long id;
    String organisation;
    @NotNull(message = "name value can not be empty")
    String fullName;
    @NotNull(message = "email value can not be empty")
    @Email(message = "value must be email")
    @Size(min=6, message = "phone number cannot be null")
    String email;
    @Size(min=6, message = "password must be min 6 max 16")
    @NotNull(message = "password value can not be empty")
    String password;
    @NotNull(message = "phoneNo value can not be empty")
    @Size(min=6, message = "phone number cannot be null")
    String phoneNo;
}
