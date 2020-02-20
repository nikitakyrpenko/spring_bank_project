package com.epam.bankproject.bankproject.entity;

import com.epam.bankproject.bankproject.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_users_request")
    private UserEntity owner;

    @Enumerated
    @Column(columnDefinition = "smallint", name = "fk_type_users",nullable = false)
    private AccountType accountType;
}
