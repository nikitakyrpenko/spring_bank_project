package com.epam.bankproject.bankproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

    @Id
    @Column(name = "account_id",nullable = false)
    private Integer id;


    @Column(name = "expiration_date",nullable = false)
    private Date expirationDate;


    @Column(name = "balance",nullable = false)
    private Double balance;

    @Column(name = "deposit_account_rate")
    private Double depositRate;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name = "credit_rate")
    private Double creditRate;

    @Column(name = "charge_per_month")
    private Double creditCharge;

    @Column(name = "credit_liabilities")
    private Double creditLiability;

    @Enumerated
    @Column(columnDefinition = "smallint", name = "fk_accounts_type_accounts",nullable = false)
    private AccessType accessType;

    @OneToMany(mappedBy = "account",
               cascade = CascadeType.ALL)
    private List<ChargeEntity> charges;

    @OneToMany(mappedBy = "senderAccount", fetch = FetchType.LAZY)
    private List<OperationEntity> payers;

    @OneToMany(mappedBy = "receiverAccount", fetch = FetchType.LAZY)
    private List<OperationEntity> receivers;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity owner;



}
