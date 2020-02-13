package com.epam.bankproject.bankproject.service.mapper.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.Charge;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.ChargeEntity;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChargeMapper implements Mapper<Charge, ChargeEntity> {

    @Autowired
    private Mapper<Account, AccountEntity> accountMapper;

    @Override
    public ChargeEntity mapDomainToEntity(Charge domain) {
        ChargeEntity chargeEntity = new ChargeEntity();
        chargeEntity.setId(domain.getId());
        chargeEntity.setCharge(domain.getChargeAmount());
        chargeEntity.setChargeType(domain.getChargeType());
        chargeEntity.setAccount(accountMapper.mapDomainToEntity(domain.getAccount()));
        return chargeEntity;
    }

    @Override
    public Charge mapEntityToDomain(ChargeEntity entity) {
        return Charge.builder()
                .id(entity.getId())
                .chargeAmount(entity.getCharge())
                .chargeType(entity.getChargeType())
                .account(accountMapper.mapEntityToDomain(entity.getAccount()))
                .build();
    }
}
