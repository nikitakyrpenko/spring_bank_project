package com.epam.bankproject.bankproject.service.mapper;

import org.springframework.stereotype.Component;


public interface Mapper<D,E> {

    E mapDomainToEntity (D domain);

    D mapEntityToDomain (E entity);

}
