package com.epam.bankproject.bankproject.mapper;

import com.epam.bankproject.bankproject.domain.*;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.ChargeEntity;
import com.epam.bankproject.bankproject.enums.ChargeType;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import com.epam.bankproject.bankproject.service.mapper.impl.ChargeMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith( SpringJUnit4ClassRunner.class )
public class ChargeMapperTest {

    @Mock
    private Mapper<Account, AccountEntity> accountMapper;

    @InjectMocks
    private ChargeMapper chargeMapper;

    @After
    public void resetMocks(){
        reset(accountMapper);
    }

    @Test
    public void whenMapDomainToEntity_thenReturnEntity() {

        Charge charge = Charge.builder()
                .id(1)
                .chargeAmount(100.0)
                .chargeType(ChargeType.CREDIT_ARRIVAL)
                .account( DepositAccount.builder().build())
                .build();

        ChargeEntity expected = new ChargeEntity();
        expected.setId(1);
        expected.setCharge(100.0);
        expected.setChargeType(ChargeType.CREDIT_ARRIVAL);

        when(accountMapper.mapDomainToEntity(any(Account.class))).thenReturn(new AccountEntity());

        ChargeEntity actual = chargeMapper.mapDomainToEntity(charge);

        verify(accountMapper,times(1)).mapDomainToEntity(any(Account.class));

        assertThat(actual).isEqualToIgnoringGivenFields(expected, "account");
    }

    @Test
    public void whenMapEntityToDomain_thenReturnDomain() {

        ChargeEntity chargeEntity = new ChargeEntity();
        chargeEntity.setId(1);
        chargeEntity.setCharge(100.0);
        chargeEntity.setChargeType(ChargeType.CREDIT_ARRIVAL);
        chargeEntity.setAccount(new AccountEntity());

        Charge expected = Charge.builder()
                .id(1)
                .chargeAmount(100.0)
                .chargeType(ChargeType.CREDIT_ARRIVAL)
                .account(DepositAccount.builder().build())
                .build();

        when(accountMapper.mapEntityToDomain(any(AccountEntity.class))).thenReturn(DepositAccount.builder().build());

        Charge actual = chargeMapper.mapEntityToDomain(chargeEntity);

        verify(accountMapper,times(1)).mapEntityToDomain(any(AccountEntity.class));

        assertThat(actual).isEqualToIgnoringGivenFields(expected, "account");
    }

}
