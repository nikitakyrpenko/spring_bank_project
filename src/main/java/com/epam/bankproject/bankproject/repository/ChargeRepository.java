package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.ChargeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ChargeRepository extends PagingAndSortingRepository<ChargeEntity, Integer> {

    List<ChargeEntity> findAllByAccountId(Integer id);

    List<ChargeEntity> findAllByAccountId(Integer id, Pageable pageable);

}
