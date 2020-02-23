package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private  Integer id;

    @NotNull
    @NotEmpty
    private  String name;

    @NonNull
    @NotEmpty
    private  String surname;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "([+]*38[(]?[0-9]{1,4}[)]?[-\\\\s./0-9]*)")
    private  String telephone;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&*+/=?`{}~^.-]+@[a-zA-Z0-9.-]+$")
    private  String email;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private  String password;

    private  Role role;

    @NotNull(message = "password not matches")
    private String passwordConfirmation;

    public void setPassword(String password){
        this.password = password;
        checkPassword();
    }

    public void setPasswordConfirmation(String passwordConfirmation){
        this.passwordConfirmation = passwordConfirmation;
        checkPassword();
    }

    private void checkPassword() {
        if(this.password == null || this.passwordConfirmation == null){
            return;
        }else if(!this.password.equals(passwordConfirmation)){
            this.passwordConfirmation = null;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
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
