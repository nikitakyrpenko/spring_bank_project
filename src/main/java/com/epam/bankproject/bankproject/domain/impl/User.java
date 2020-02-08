package com.epam.bankproject.bankproject.domain.impl;

import com.epam.bankproject.bankproject.enums.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder(toBuilder = true)
@Data
public class User {

    private final Integer id;

    @NotNull
    @NotEmpty
    private final String name;

    @NotNull
    @NotEmpty
    private final String surname;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "([+]*38[(]?[0-9]{1,4}[)]?[-\\\\s./0-9]*)")
    private final String telephone;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&*+/=?`{}~^.-]+@[a-zA-Z0-9.-]+$")
    private final String email;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%/]).{6,20})")
    private final String password;

    @NotNull
    private final Role role;
}
