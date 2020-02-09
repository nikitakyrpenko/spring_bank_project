package com.epam.bankproject.bankproject.service.mapper.impl;

import com.epam.bankproject.bankproject.domain.impl.User;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.NonNull;
import org.springframework.stereotype.Component;


@Component
public class UserMapper implements Mapper<User, UserEntity> {
    @Override
    public UserEntity mapDomainToEntity(@NonNull User domain) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(domain.getId());
        userEntity.setName(domain.getName());
        userEntity.setSurname(domain.getSurname());
        userEntity.setEmail(domain.getEmail());
        userEntity.setPassword(domain.getPassword());
        userEntity.setTelephone(domain.getTelephone());
        userEntity.setRole(domain.getRole());
        return userEntity;
    }

    @Override
    public User mapEntityToDomain(@NonNull UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .telephone(entity.getTelephone())
                .role(entity.getRole())
                .build();
    }
}
