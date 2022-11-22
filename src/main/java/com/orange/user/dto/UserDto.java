package com.orange.user.dto;

import com.orange.user.util.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String login;
    private String password;
    private boolean isActive;
    private Roles role;
}
