package com.epam.bankproject.bankproject.service.mapper.impl;

import com.epam.bankproject.bankproject.domain.Request;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.entity.RequestEntity;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper implements Mapper<Request, RequestEntity> {

    @Autowired
    private Mapper<User, UserEntity> userMapper;

    @Override
    public RequestEntity mapDomainToEntity(Request domain) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setOwner(userMapper.mapDomainToEntity(domain.getOwner()));
        requestEntity.setAccountType(domain.getAccountType());
        return requestEntity;
    }

    @Override
    public Request mapEntityToDomain(RequestEntity entity) {
        return Request.builder()
                .id(entity.getId())
                .accountType(entity.getAccountType())
                .owner(userMapper.mapEntityToDomain(entity.getOwner()))
                .build();
    }
}
