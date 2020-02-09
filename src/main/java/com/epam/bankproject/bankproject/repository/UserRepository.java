package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(@NonNull String email);

}
