package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.ChargeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeRepository extends PagingAndSortingRepository<ChargeEntity, Integer> {

    List<ChargeEntity> findAllByAccountId(Integer id);

    Page<ChargeEntity> findAllByAccountId(Integer id, Pageable pageable);

    long countAllByAccountId(Integer id);

}
