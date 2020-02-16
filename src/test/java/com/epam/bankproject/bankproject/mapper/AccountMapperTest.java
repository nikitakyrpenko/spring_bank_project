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
import com.epam.bankproject.bankproject.service.mapper.impl.AccountMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.sql.Date;

@RunWith( MockitoJUnitRunner.class )
public class AccountMapperTest {

    private static final UserEntity USER_ENTITY = initUserEntity();
    private static final User USER = initUser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Mapper<User, UserEntity> userMapper;

    @InjectMocks
    private AccountMapper accountMapper;

    @After
    public void resetMocks(){
        reset(userMapper);
    }

    @Test
    public void whenMapDepositDomainToEntity_thenReturnEntity(){

        Account account = DepositAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .depositAmount(1000.0)
                .depositRate(0.1)
                .accountType(AccountType.DEPOSIT)
                .owner(new User())
                .build();

        when(userMapper.mapDomainToEntity(any(User.class))).thenReturn(USER_ENTITY);

        AccountEntity actual = accountMapper.mapDomainToEntity(account);

        AccountEntity expect = new AccountEntity();
        expect.setId(1);
        expect.setExpirationDate(Date.valueOf("2021-03-12"));
        expect.setBalance(1000.0);
        expect.setDepositRate(0.1);
        expect.setAccountType(AccountType.DEPOSIT);

        verify(userMapper, times(1)).mapDomainToEntity(any(User.class));

        assertThat(actual).isEqualToIgnoringGivenFields(expect,"owner");
    }

    @Test
    public void whenMapCreditDomainToEntity_thenReturnEntity(){
        Account account = CreditAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .accountType(AccountType.CREDIT)
                .owner(new User())
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

        when(userMapper.mapDomainToEntity(any(User.class))).thenReturn(USER_ENTITY);

        AccountEntity actual = accountMapper.mapDomainToEntity(account);

        verify(userMapper, times(1)).mapDomainToEntity(any(User.class));

        assertThat(actual).isEqualToIgnoringGivenFields(expect,"owner");
    }

    @Test
    public void whenMapEntityToCreditDomain_thenReturnDomain(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setExpirationDate(Date.valueOf("2021-03-12"));
        accountEntity.setBalance(1000.0);
        accountEntity.setCreditRate(0.2);
        accountEntity.setCreditLimit(50000.0);
        accountEntity.setCreditLiability(0.0);
        accountEntity.setCreditCharge(1858.17);
        accountEntity.setAccountType(AccountType.CREDIT);
        accountEntity.setOwner(new UserEntity());

        Account expected = CreditAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .creditRate(0.2)
                .limit(50000.0)
                .liability(0.0)
                .accountType(AccountType.CREDIT)
                .build();

        when(userMapper.mapEntityToDomain(any(UserEntity.class))).thenReturn(USER);

        Account actual = accountMapper.mapEntityToDomain(accountEntity);

        verify(userMapper, times(1)).mapEntityToDomain(any(UserEntity.class));

        assertThat(actual).isEqualToIgnoringGivenFields(expected,"owner");
    }

    @Test
    public void whenMapEntityToDepositDomain_thenReturnDomain(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setExpirationDate(Date.valueOf("2021-03-12"));
        accountEntity.setBalance(1000.0);
        accountEntity.setDepositRate(0.2);
        accountEntity.setAccountType(AccountType.DEPOSIT);
        accountEntity.setOwner(new UserEntity());

        Account expected = DepositAccount.builder()
                .id(1)
                .balance(1000.0)
                .depositAmount(1000.0)
                .expirationDate(Date.valueOf("2021-03-12"))
                .depositRate(0.2)
                .accountType(AccountType.DEPOSIT)
                .build();

        when(userMapper.mapEntityToDomain(any(UserEntity.class))).thenReturn(USER);

        Account actual = accountMapper.mapEntityToDomain(accountEntity);

        verify(userMapper, times(1)).mapEntityToDomain(any(UserEntity.class));

        assertThat(actual).isEqualToIgnoringGivenFields(expected,"owner");
    }


    private static UserEntity initUserEntity(){
        return new UserEntity();
    }

    private static User initUser(){
        return new User();
    }
}
