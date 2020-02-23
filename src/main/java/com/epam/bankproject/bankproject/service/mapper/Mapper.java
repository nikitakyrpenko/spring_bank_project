package com.epam.bankproject.bankproject.service.mapper;

public interface Mapper<D,E> {

    E mapDomainToEntity (D domain);

    D mapEntityToDomain (E entity);

}
