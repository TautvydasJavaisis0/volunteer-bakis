package com.tietoevry.backend.user;

import com.tietoevry.backend.user.model.Role;
import com.tietoevry.backend.user.model.User;
import com.tietoevry.backend.user.model.dto.UserDto;
import com.tietoevry.backend.user.model.dto.UserStatsDto;

import java.util.List;
import java.util.Optional;

public class UserMapper {
    private UserMapper() {}

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .organisation(user.getOrganisation())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .password(user.getPassword())
            .phoneNo(user.getPhone())
            .build();
    }

    public static UserStatsDto toUserStatsDto(int days, int rating, int totalAttended, int totalApplied) {
        return UserStatsDto.builder()
            .totalDays(days)
            .rating(rating)
            .totalAttended(totalAttended)
            .totalApplied(totalApplied)
            .build();
    }

    public static User toUser(UserDto userDto, Optional<List<Role>> roleList) {
        return User.builder()
            .id(userDto.getId())
            .organisation(userDto.getOrganisation())
            .fullName(userDto.getFullName())
            .email(userDto.getEmail())
            .password(userDto.getPassword())
            .phone(userDto.getPhoneNo())
            .role(roleList.get())
            .build();
    }
}
