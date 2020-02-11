package com.epam.bankproject.bankproject.service.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.impl.Operation;
import com.epam.bankproject.bankproject.entity.OperationEntity;
import com.epam.bankproject.bankproject.repository.OperationRepository;
import com.epam.bankproject.bankproject.service.OperationService;
import com.epam.bankproject.bankproject.service.exception.MonetaryException;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class OperationServiceImpl implements OperationService {


    private OperationRepository operationRepository;
    private Mapper<Operation, OperationEntity> operationMapper;

    @Override
    public Operation save(@NonNull Operation operation){
        if (operation.getSender().getBalance() < operation.getTransfer()){
            throw new MonetaryException("Not enough balance to perform such operation");
        }
        Account sender   = operation.getSender();
        Account receiver = operation.getReceiver();

        sender.processTransfer(operation);
        receiver.processTransfer(operation);

        OperationEntity operationEntity = operationMapper.mapDomainToEntity(operation);

        return operationMapper.mapEntityToDomain(operationRepository.save(operationEntity));
    }

    @Override
    public List<Operation> findAllOperationsByAccountId(@NonNull Integer id, @NonNull Pageable pageable) {
        return operationRepository.findAllOperationsByAccountId(id, pageable)
                .stream()
                .map(operationMapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Operation> findAllByReceiverAccountId(@NonNull Integer id, @NonNull Pageable pageable) {
        return operationRepository.findAllByReceiverAccountId(id, pageable)
                .stream()
                .map(operationMapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Operation> findAllBySenderAccountId(@NonNull Integer id, @NonNull Pageable pageable) {
        return operationRepository.findAllBySenderAccountId(id, pageable)
                .stream()
                .map(operationMapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());
    }
}
