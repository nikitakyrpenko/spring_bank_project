package com.epam.bankproject.bankproject.service.impl;

import com.epam.bankproject.bankproject.domain.InterestChargeable;
import com.epam.bankproject.bankproject.domain.impl.Charge;
import com.epam.bankproject.bankproject.entity.ChargeEntity;
import com.epam.bankproject.bankproject.repository.ChargeRepository;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.ChargeService;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ChargeServiceImpl implements ChargeService {

    private AccountService accountService;
    private ChargeRepository chargeRepository;
    private Mapper<Charge, ChargeEntity> chargeMapper;


    //TODO HOW TO TEST
    public void applyChargesByEndOfMonth(){
        accountService.findAll()
                .stream()
                .map(account -> {
                    InterestChargeable interestChargeable = (InterestChargeable) account;
                    return interestChargeable.processCharge();
                }).forEach(this::save);
    }

    @Override
    public List<Charge> findAllByAccountId(@NonNull Integer id) {
        return chargeRepository.findAllByAccountId(id)
                .stream()
                .map(chargeMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Charge> findAllByAccountId(@NonNull Integer id, @NonNull Pageable pageable) {
        return chargeRepository.findAllByAccountId(id, pageable)
                .stream()
                .map(chargeMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(@NonNull Charge charge) {
        ChargeEntity chargeEntity = chargeMapper.mapDomainToEntity(charge);
        ChargeEntity save = chargeRepository.save(chargeEntity);
    }
}
