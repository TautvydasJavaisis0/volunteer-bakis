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
public class RateUserDto {
    Long userId;
    Long initiativeId;
    boolean attended;
    boolean rated;
}
