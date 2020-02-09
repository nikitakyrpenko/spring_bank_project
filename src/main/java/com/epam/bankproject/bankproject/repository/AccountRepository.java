package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.AccountEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Integer> {


    List<AccountEntity> findAllByOwnerId(@NotNull Integer id, Pageable pageable);

    List<AccountEntity> findAllByOwnerId(@NotNull Integer id);


}
