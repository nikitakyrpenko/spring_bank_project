package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {

    void save(Request request);

    Page<Request> findAll(Pageable pageable);

    long countAll();

    void deleteById(Integer id);

    Request findById(Integer id);
}
