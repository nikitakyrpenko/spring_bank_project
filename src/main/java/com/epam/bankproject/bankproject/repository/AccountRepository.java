package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.AccountEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Integer> {

    Page<AccountEntity> findAllByOwnerId(@NonNull Integer id, Pageable pageable);

    List<AccountEntity> findAllByOwnerId(@NonNull Integer id);

    List<AccountEntity> findAll();

    List<AccountEntity> findAllByExpirationDate(Date date);

    long countAllByOwnerId(Integer id);

}
