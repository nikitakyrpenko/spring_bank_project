package com.epam.bankproject.bankproject.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operations")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private Integer id;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Column(name = "operation_date", nullable = false)
    private Date operationDate;

    @Column(name = "transfer", nullable = false)
    private Double transfer;

    @ManyToOne
    @JoinColumn(name = "fk_accounts_sender",nullable = false)
    private AccountEntity senderAccount;

    @ManyToOne
    @JoinColumn(name = "fk_accounts_receiver",nullable = false)
    private AccountEntity receiverAccount;

}
