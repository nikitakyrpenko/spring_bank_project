package com.epam.bankproject.bankproject.mapper;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.CreditAccount;
import com.epam.bankproject.bankproject.domain.DepositAccount;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.Role;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Date;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class AccountMapperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private Mapper<Account, AccountEntity> accountMapper;

    @Test
    public void whenMapDepositDomainToEntity_thenReturnEntity(){
        User me = User.builder()
                .id(1)
                .name("Jon")
                .surname("Doe")
                .email("jondoe@gmail.com")
                .password("P@ssword97")
                .telephone("380508321899")
                .role(Role.ROLE_USER)
                .build();

        Account account = DepositAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .depositAmount(1000.0)
                .depositRate(0.1)
                .accountType(AccountType.DEPOSIT)
                .owner(me)
                .build();

        AccountEntity actual = accountMapper.mapDomainToEntity(account);

        AccountEntity expect = new AccountEntity();
        expect.setId(1);
        expect.setExpirationDate(Date.valueOf("2021-03-12"));
        expect.setBalance(1000.0);
        expect.setDepositRate(0.1);
        expect.setAccountType(AccountType.DEPOSIT);

        assertThat(actual).isEqualToIgnoringGivenFields(expect,"owner");
    }

    @Test
    public void whenMapCreditDomainToEntity_thenReturnEntity(){
        User me = User.builder()
                .id(1)
                .name("Jon")
                .surname("Doe")
                .email("jondoe@gmail.com")
                .password("P@ssword97")
                .telephone("380508321899")
                .role(Role.ROLE_USER)
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

        AccountEntity expect = new AccountEntity();
        expect.setId(1);
        expect.setExpirationDate(Date.valueOf("2021-03-12"));
        expect.setBalance(1000.0);
        expect.setCreditRate(0.1);
        expect.setCreditLimit(1000.0);
        expect.setCreditLiability(0.0);
        expect.setCreditCharge(account.getCharge());
        expect.setAccountType(AccountType.CREDIT);

        AccountEntity actual = accountMapper.mapDomainToEntity(account);
        assertThat(actual).isEqualToIgnoringGivenFields(expect,"owner");
    }

    @Test
    public void whenMapEntityToCreditDomain_thenReturnDomain(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Freya");
        userEntity.setSurname("Doe");
        userEntity.setEmail("dolor.Donec@etmagnaPraesent.net");
        userEntity.setTelephone("380984895779");
        userEntity.setPassword("P@ssword97");
        userEntity.setRole(Role.ROLE_USER);

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

        Account expected = CreditAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .creditRate(0.2)
                .limit(50000.0)
                .liability(0.0)
                .accountType(AccountType.CREDIT)
                .build();

        Account actual = accountMapper.mapEntityToDomain(accountEntity);
        assertThat(actual).isEqualToIgnoringGivenFields(expected,"owner");
    }

    @Test
    public void whenMapEntityToDepositDomain_thenReturnDomain(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Freya");
        userEntity.setSurname("Doe");
        userEntity.setEmail("dolor.Donec@etmagnaPraesent.net");
        userEntity.setTelephone("380984895779");
        userEntity.setPassword("P@ssword97");
        userEntity.setRole(Role.ROLE_USER);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setExpirationDate(Date.valueOf("2021-03-12"));
        accountEntity.setBalance(1000.0);
        accountEntity.setDepositRate(0.2);
        accountEntity.setAccountType(AccountType.DEPOSIT);
        accountEntity.setOwner(userEntity);

        Account expected = DepositAccount.builder()
                .id(1)
                .balance(1000.0)
                .depositAmount(1000.0)
                .expirationDate(Date.valueOf("2021-03-12"))
                .depositRate(0.2)
                .accountType(AccountType.DEPOSIT)
                .build();

        Account actual = accountMapper.mapEntityToDomain(accountEntity);
        assertThat(actual).isEqualToIgnoringGivenFields(expected,"owner");
    }

}
