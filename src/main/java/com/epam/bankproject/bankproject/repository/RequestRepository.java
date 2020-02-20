package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.RequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<RequestEntity, Integer> {

    Page<RequestEntity> findAll(Pageable pageable);


}
