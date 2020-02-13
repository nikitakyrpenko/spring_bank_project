package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Charge;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChargeService {

    List<Charge> findAllByAccountId(Integer id);

    List<Charge> findAllByAccountId(Integer id,Pageable pageable);

    void save(Charge charge);
}
