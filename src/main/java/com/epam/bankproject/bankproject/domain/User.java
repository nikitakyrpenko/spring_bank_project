package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.EnumSet;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return EnumSet.allOf(Role.class);
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
