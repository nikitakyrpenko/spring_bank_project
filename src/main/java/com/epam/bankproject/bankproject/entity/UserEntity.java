package com.epam.bankproject.bankproject.entity;


import com.epam.bankproject.bankproject.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Integer id;

    @Column(name = "firstname",nullable = false)
    @NotEmpty(message = "Users name should not be empty")
    private String name;

    @Column(name = "surname",nullable = false)
    @NotEmpty(message = "Users surname should not be empty")
    private String surname;

    @Column(name = "email",unique = true,nullable = false)
    @NotEmpty(message = "Users email should not be empty")
    @Email
    private String email;

    @Column(name = "passwords",nullable = false)
    @NotEmpty(message = "Users password should not be empty")
    private String password;

    @Column(name = "telephone",nullable = false)
    @NotEmpty(message = "Users telephone should not be empty")
    private String telephone;

    @Enumerated
    @Column(columnDefinition = "smallint", name = "fk_roles_users",nullable = false)
    private Role role;

    @OneToMany( mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AccountEntity> accounts;




}
