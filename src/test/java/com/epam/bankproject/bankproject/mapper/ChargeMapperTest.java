package com.epam.bankproject.bankproject.mapper;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.impl.Charge;
import com.epam.bankproject.bankproject.domain.impl.CreditAccount;
import com.epam.bankproject.bankproject.domain.impl.User;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.ChargeEntity;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.ChargeType;
import com.epam.bankproject.bankproject.enums.Role;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class ChargeMapperTest {

    @Autowired
    private Mapper<Charge, ChargeEntity> chargeMapper;

    @Autowired
    private Mapper<Account, AccountEntity> accountMapper;

    @Test
    public void whenMapDomainToEntity_thenReturnEntity() {
        User me = User.builder()
                .id(1)
                .name("Jon")
                .surname("Doe")
                .email("jondoe@gmail.com")
                .password("P@ssword97")
                .telephone("380508321899")
                .role(Role.CLIENT)
                .build();

        Account account = CreditAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .accountType(AccountType.CREDIT)
                .owner(me)
                .limit(1000.0)
                .creditRate(0.1)
                .liability(0.0)
                .build();

        Charge charge = Charge.builder()
                .id(1)
                .chargeAmount(100.0)
                .chargeType(ChargeType.CREDIT_ARRIVAL)
                .account(account)
                .build();

        ChargeEntity expected = new ChargeEntity();
        expected.setId(1);
        expected.setCharge(100.0);
        expected.setChargeType(ChargeType.CREDIT_ARRIVAL);
        expected.setAccount(accountMapper.mapDomainToEntity(account));

        ChargeEntity actual = chargeMapper.mapDomainToEntity(charge);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenMapEntityToDomain_thenReturnDomain() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Jon");
        userEntity.setSurname("Doe");
        userEntity.setEmail("jondoe@gmail.com");
        userEntity.setPassword("P@ssword97");
        userEntity.setTelephone("380508321899");
        userEntity.setRole(Role.CLIENT);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setExpirationDate(Date.valueOf("2021-03-12"));
        accountEntity.setBalance(1000.0);
        accountEntity.setCreditRate(0.2);
        accountEntity.setCreditLimit(50000.0);
        accountEntity.setCreditLiability(0.0);
        accountEntity.setCreditCharge(1858.17);
        accountEntity.setAccountType(AccountType.CREDIT);
        accountEntity.setOwner(userEntity);

        ChargeEntity chargeEntity = new ChargeEntity();
        chargeEntity.setId(1);
        chargeEntity.setCharge(100.0);
        chargeEntity.setChargeType(ChargeType.CREDIT_ARRIVAL);
        chargeEntity.setAccount(accountEntity);

        Charge expected = Charge.builder()
                .id(1)
                .chargeAmount(100.0)
                .chargeType(ChargeType.CREDIT_ARRIVAL)
                .account(accountMapper.mapEntityToDomain(accountEntity))
                .build();

        Charge actual = chargeMapper.mapEntityToDomain(chargeEntity);

        assertThat(actual).isEqualTo(expected);
    }

}
