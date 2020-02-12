package com.epam.bankproject.bankproject.domain.impl;

import com.epam.bankproject.bankproject.enums.Role;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private  Integer id;

    @NonNull
    @NotEmpty
    private  String name;

    @NonNull
    @NotEmpty
    private  String surname;

    @NonNull
    @NotEmpty
    @Pattern(regexp = "([+]*38[(]?[0-9]{1,4}[)]?[-\\\\s./0-9]*)")
    private  String telephone;

    @NonNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&*+/=?`{}~^.-]+@[a-zA-Z0-9.-]+$")
    private  String email;

    @NonNull
    @NotEmpty
    @Pattern(regexp = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%/]).{6,20})")
    private  String password;

    private  Role role;
}
