package com.epam.bankproject.bankproject.service.mapper.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.impl.Operation;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.OperationEntity;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperationMapper implements Mapper<Operation, OperationEntity> {

    @Autowired
    private Mapper<Account, AccountEntity> accountMapper;

    @Override
    public OperationEntity mapDomainToEntity(Operation domain) {
        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setId(domain.getId());
        operationEntity.setPurpose(domain.getPurpose());
        operationEntity.setTransfer(domain.getTransfer());
        operationEntity.setOperationDate(domain.getDateOfOperation());
        operationEntity.setSenderAccount(accountMapper.mapDomainToEntity(domain.getSender()));
        operationEntity.setReceiverAccount(accountMapper.mapDomainToEntity(domain.getReceiver()));
        return operationEntity;

    }

    @Override
    public Operation mapEntityToDomain(OperationEntity entity) {
        return Operation.builder()
                .id(entity.getId())
                .purpose(entity.getPurpose())
                .dateOfOperation(entity.getOperationDate())
                .transfer(entity.getTransfer())
                .sender(accountMapper.mapEntityToDomain(entity.getSenderAccount()))
                .receiver(accountMapper.mapEntityToDomain(entity.getReceiverAccount()))
                .build();
    }
}
