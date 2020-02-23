package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Charge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ChargeService {

    void applyChargesByEndOfMonth();

    List<Charge> findAllByAccountId(Integer id);

    Page<Charge> findAllByAccountId(Integer id, Pageable pageable);

    void save(Charge charge);

    long countAllByAccountId(Integer id);
}
