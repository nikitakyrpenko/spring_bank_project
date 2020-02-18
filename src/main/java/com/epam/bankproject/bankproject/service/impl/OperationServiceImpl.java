package com.epam.bankproject.bankproject.service.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.Operation;
import com.epam.bankproject.bankproject.entity.OperationEntity;
import com.epam.bankproject.bankproject.repository.AccountRepository;
import com.epam.bankproject.bankproject.repository.OperationRepository;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.OperationService;
import com.epam.bankproject.bankproject.service.exception.MonetaryException;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class OperationServiceImpl implements OperationService {

    private OperationRepository operationRepository;
    private AccountService accountService;
    private Mapper<Operation, OperationEntity> operationMapper;

    @Override
    @Transactional
    public Operation save(@NonNull Operation operation) {
        Account sender = accountService.findById(operation.getSender().getId());
        Account receiver = accountService.findById(operation.getReceiver().getId());
        operation.setSender(sender);
        operation.setReceiver(receiver);
        if (sender.getBalance() < operation.getTransfer()) {
            throw new MonetaryException("Not enough balance to perform such operation");
        }

        sender.processTransfer(operation);
        receiver.processTransfer(operation);

        accountService.save(sender);
        accountService.save(receiver);

        OperationEntity operationEntity = operationMapper.mapDomainToEntity(operation);

        return operationMapper.mapEntityToDomain(operationRepository.save(operationEntity));
    }

    @Override
    public Page<Operation> findAllOperationsByAccountId(@NonNull Integer id, @NonNull Pageable pageable) {
        List<Operation> operations;

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        long totalRecords = operationRepository.countAllByReceiverAccountIdOrSenderAccountId(id);

        operations = operationRepository.findAllOperationsByAccountId(id, pageable)
                .stream()
                .map(operationMapper::mapEntityToDomain)
                .collect(Collectors.toUnmodifiableList());

        return new PageImpl<Operation>(operations, PageRequest.of(currentPage, pageSize), totalRecords);
    }

    //TODO CHECK FOR PAGEABLE
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

    @Override
    public long countAllByReceiverAccountIdOrSenderAccountId(Integer id) {
        return operationRepository.countAllByReceiverAccountIdOrSenderAccountId(id);
    }
}
