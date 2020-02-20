package com.epam.bankproject.bankproject.entity;

import com.epam.bankproject.bankproject.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id",nullable = false)
    private Integer id;

    @Column(name = "expiration_date",nullable = false)
    private Date expirationDate;

    @Column(name = "balance",nullable = false)
    private Double balance;

    @Column(name = "deposit_account_rate")
    private Double depositRate= 0.0;

    @Column(name = "credit_limit")
    private Double creditLimit = 0.0;

    @Column(name = "credit_rate")
    private Double creditRate = 0.0;

    @Column(name = "charge_per_month")
    private Double creditCharge = 0.0;

    @Column(name = "credit_liabilities")
    private Double creditLiability = 0.0;

    @Enumerated
    @Column(columnDefinition = "smallint", name = "fk_accounts_type_accounts",nullable = false)
    private AccountType accountType;

    @OneToMany(mappedBy = "account",
               cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChargeEntity> charges;

    @OneToMany(mappedBy = "senderAccount", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<OperationEntity> payers;

    @OneToMany(mappedBy = "receiverAccount", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<OperationEntity> receivers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_users_accounts")
    @ToString.Exclude
    private UserEntity owner;



}
