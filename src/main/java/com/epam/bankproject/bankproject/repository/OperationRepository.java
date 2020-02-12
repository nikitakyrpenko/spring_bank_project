package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.OperationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OperationRepository extends PagingAndSortingRepository<OperationEntity, Integer> {

    @Query(value = "SELECT o FROM OperationEntity o WHERE o.receiverAccount.id = :id or o.senderAccount.id = :id")
    List<OperationEntity> findAllOperationsByAccountId(@Param("id") Integer id, Pageable pageable);

    @Query(value = "SELECT o FROM OperationEntity o WHERE o.receiverAccount.id = :id or o.senderAccount.id = :id")
    List<OperationEntity> findAllOperationsByAccountId(@Param("id") Integer id);

    List<OperationEntity> findAllByReceiverAccountId(Integer id, Pageable pageable);

    List<OperationEntity> findAllBySenderAccountId(Integer id, Pageable pageable);
}
