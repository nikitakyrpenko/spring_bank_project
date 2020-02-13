package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.repository.AccountRepository;
import com.epam.bankproject.bankproject.service.impl.AccountServiceImpl;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.sql.Date;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final PageRequest page = PageRequest.of(0,2);
    private static final List<AccountEntity> accounts = new ArrayList<>();
    public static final  AccountEntity accountEntity;

    static {
        //TODO SET VIA CONSTRUCTOR
        accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setExpirationDate(Date.valueOf("2021-03-12"));
        accountEntity.setBalance(1000.0);
        accountEntity.setCreditRate(0.2);
        accountEntity.setCreditLimit(50000.0);
        accountEntity.setCreditLiability(0.0);
        accountEntity.setCreditCharge(1858.17);
        accountEntity.setAccountType(AccountType.CREDIT);

        accounts.add(accountEntity);
    }

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Mapper<Account, AccountEntity> accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @After
    public void resetMocks(){
        reset(accountRepository,accountMapper);
    }

    @Test
    public void whenAccountServiceFindAllPageable_thenReturnAccountCollection(){
        List<AccountEntity> expected = new ArrayList<>();
        Page<AccountEntity> expectedPage = new PageImpl(expected);
        AccountEntity accountEntity = mock(AccountEntity.class);

        when(accountRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        accountService.findAll(page);

        verify(accountRepository,atLeastOnce()).findAll(page);
    }

    @Test
    public void whenAccountServiceFindById_thenReturnAccount(){
        AccountEntity accountEntity = new AccountEntity();
        Account account = mock(Account.class);
        when(accountRepository.findById(any(Integer.class))).thenReturn(Optional.of(accountEntity));
        when(accountMapper.mapEntityToDomain(any(AccountEntity.class))).thenReturn(account);

        accountService.findById(1);

        verify(accountRepository,times(1)).findById(any(Integer.class));
        verify(accountMapper,times(1)).mapEntityToDomain(any(AccountEntity.class));


    }

    @Test
    public void whenAccountServiceFindByIdPassNullParameter_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("id is marked non-null but is null");

        AccountEntity accountEntity = mock(AccountEntity.class);

        accountService.findById(null);

        verify(accountMapper, never()).mapEntityToDomain(accountEntity);

    }

    @Test
    public void whenAccountServiceFindByIdNotExist_thenThrowNoSuchElementException(){
        AccountEntity accountEntity = mock(AccountEntity.class);

        expectedException.expect(NoSuchElementException.class);
        expectedException.expectMessage("No value present");

        accountService.findById(1000);

        verify(accountMapper, never()).mapEntityToDomain(accountEntity);
    }


    //TODO fix test
    @Test
    public void whenFindByOwnerIdPageable_thenReturnAccountCollection(){

       /* Account account = mock(Account.class);

        when(accountRepository.findAllByOwnerId(any(Integer.class), any(Pageable.class))).thenReturn(accounts);
        when(accountMapper.mapEntityToDomain(any(AccountEntity.class))).thenReturn(account);

        accountService.findAllByOwnerId(1,page);

        verify(accountRepository,times(1)).findAllByOwnerId(1,page);
        verify(accountMapper,atLeastOnce()).mapEntityToDomain(any(AccountEntity.class));*/
    }

    @Test
    public void whenFindByOwnerIdPassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("id is marked non-null but is null");
        accountService.findAllByOwnerId(null,PageRequest.of(1,2));
    }

    @Test
    public void whenFindByOwnerPagePassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("pageable is marked non-null but is null");
        accountService.findAllByOwnerId(1,null);
    }

    @Test
    public void whenSave_thenReturnAccount(){
        AccountEntity mockEntity = mock(AccountEntity.class);
        Account mockDomain = mock(Account.class);

        when(accountRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);
        when(accountMapper.mapDomainToEntity(any(Account.class))).thenReturn(mockEntity);
        when(accountMapper.mapEntityToDomain(any(AccountEntity.class))).thenReturn(mockDomain);

        accountService.save(mockDomain);

        verify(accountRepository,times(1)).save(any(AccountEntity.class));
        verify(accountMapper,times(1)).mapEntityToDomain(any(AccountEntity.class));
        verify(accountMapper,times(1)).mapDomainToEntity(any(Account.class));
    }

    @Test
    public void whenSaveIsNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("account is marked non-null but is null");
        accountService.save(null);
    }

    @Test
    public void whenFindAll_thenReturnAccountCollection(){
        Account account = mock(Account.class);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(accountMapper.mapEntityToDomain(any(AccountEntity.class))).thenReturn(account);

        accountService.findAll();

        verify(accountRepository,times(1)).findAll();
        verify(accountMapper,atLeastOnce()).mapEntityToDomain(any(AccountEntity.class));
    }
}
