package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.impl.Charge;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChargeService {

    List<Charge> findAllByAccountId(@NonNull Integer id);

    List<Charge> findAllByAccountId(@NonNull Integer id, @NonNull Pageable pageable);

    void save(@NonNull Charge charge);
}
