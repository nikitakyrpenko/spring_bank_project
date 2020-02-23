package com.epam.bankproject.bankproject.service.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.Request;
import com.epam.bankproject.bankproject.entity.RequestEntity;
import com.epam.bankproject.bankproject.repository.RequestRepository;
import com.epam.bankproject.bankproject.service.RequestService;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class RequestServiceImpl implements RequestService {


    private Mapper<Request, RequestEntity> requestMapper;
    private RequestRepository requestRepository;

    @Override
    public void save(Request request) {
        requestRepository.save(requestMapper.mapDomainToEntity(request));
    }

    @Override
    public Page<Request> findAll(Pageable pageable) {
        List<Request> requests;

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        long totalRecords = requestRepository.count();

        requests = requestRepository.findAll(pageable)
                .stream()
                .map(requestMapper::mapEntityToDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(requests, PageRequest.of(currentPage,pageSize),totalRecords);
    }

    @Override
    public void deleteById(Integer id) {
        requestRepository.deleteById(id);
    }

    @Override
    public Request findById(Integer id) {
        return requestRepository.findById(id)
                .map(requestMapper::mapEntityToDomain)
                .orElseThrow();
    }

    @Override
    public long countAll() {
        return requestRepository.count();
    }
}
