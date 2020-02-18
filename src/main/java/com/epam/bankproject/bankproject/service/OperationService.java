package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OperationService {

    Operation save(Operation operation);

    Page<Operation> findAllOperationsByAccountId(Integer id, Pageable pageable);

    List<Operation> findAllByReceiverAccountId(Integer id, Pageable pageable);

    List<Operation> findAllBySenderAccountId(Integer id, Pageable pageable);

    long countAllByReceiverAccountIdOrSenderAccountId(Integer id);
}
