package com.epam.bankproject.bankproject.entity;

import com.epam.bankproject.bankproject.enums.ChargeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "charges")
public class ChargeEntity {

    @Id
    @Column(name = "charge_id")
    private Integer id;

    @Column(name = "charge", nullable = false)
    private Double charge;

    @Enumerated
    @Column(columnDefinition = "smallint", name = "fk_charge_types_charge",nullable = false)
    private ChargeType chargeType;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity account;



}
