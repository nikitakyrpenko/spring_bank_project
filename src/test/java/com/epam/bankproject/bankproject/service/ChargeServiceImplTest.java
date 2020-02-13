package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.Charge;
import com.epam.bankproject.bankproject.domain.CreditAccount;
import com.epam.bankproject.bankproject.domain.DepositAccount;
import com.epam.bankproject.bankproject.entity.ChargeEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.ChargeType;
import com.epam.bankproject.bankproject.repository.ChargeRepository;
import com.epam.bankproject.bankproject.service.impl.ChargeServiceImpl;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ChargeServiceImplTest {

    private static final ChargeEntity CHARGE_ENTITY;
    private static final List<ChargeEntity> CHARGES = new ArrayList<>();
    public static final List<Account> ACCOUNTS = new ArrayList<>();

    static {

        Account credit = CreditAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .accountType(AccountType.CREDIT)
                .limit(1000.0)
                .creditRate(0.1)
                .liability(0.0)
                .build();

        Account deposit = DepositAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .depositAmount(1000.0)
                .depositRate(0.1)
                .accountType(AccountType.DEPOSIT)
                .build();

        ACCOUNTS.add(credit);
        ACCOUNTS.add(deposit);

        CHARGE_ENTITY = new ChargeEntity();
        CHARGE_ENTITY.setId(1);
        CHARGE_ENTITY.setCharge(100.0);
        CHARGE_ENTITY.setChargeType(ChargeType.CREDIT_ARRIVAL);

        CHARGES.add(CHARGE_ENTITY);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private AccountService accountService;

    @Mock
    private ChargeRepository chargeRepository;

    @Mock
    private Mapper<Charge, ChargeEntity> chargeMapper;

    @InjectMocks
    private ChargeServiceImpl chargeService;

    @After
    public void resetMocks(){
        reset(chargeMapper,chargeRepository,accountService);
    }

    @Test
    public void whenFindAllByAccountId_thenReturnChargeCollection(){

        when(chargeRepository.findAllByAccountId(any(Integer.class))).thenReturn(CHARGES);
        when(chargeMapper.mapEntityToDomain(any(ChargeEntity.class))).thenReturn(any(Charge.class));

        chargeService.findAllByAccountId(1);

        verify(chargeRepository, times(1)).findAllByAccountId(any(Integer.class));
        verify(chargeMapper,atLeastOnce()).mapEntityToDomain(any(ChargeEntity.class));
    }

    @Test
    public void whenFindAllByAccountIdPassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("id is marked non-null but is null");
        chargeService.findAllByAccountId(null);
    }

    @Test
    public void whenFindAllByAccountIdPageable_thenReturnChargeCollection(){
        Charge chargeMock = mock(Charge.class);

        when(chargeRepository.findAllByAccountId(any(Integer.class), any(Pageable.class))).thenReturn(CHARGES);
        when(chargeMapper.mapEntityToDomain(any(ChargeEntity.class))).thenReturn(chargeMock);

        chargeService.findAllByAccountId(1, PageRequest.of(0,1));

        verify(chargeRepository, times(1)).findAllByAccountId(any(Integer.class),any(Pageable.class));
        verify(chargeMapper,atLeastOnce()).mapEntityToDomain(any(ChargeEntity.class));
    }

    @Test
    public void whenFindAllAccountIdPassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("id is marked non-null but is null");
        chargeService.findAllByAccountId(null, PageRequest.of(0,1));
    }

    @Test
    public void whenFindAllAccountIdPageablePassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("pageable is marked non-null but is null");
        chargeService.findAllByAccountId(1, null);
    }

    @Test
    public void whenSave_thenReturnCharge(){
        Charge chargeMock = mock(Charge.class);
        ChargeEntity chargeEntityMock = mock(ChargeEntity.class);

        when(chargeMapper.mapDomainToEntity(any(Charge.class))).thenReturn(chargeEntityMock);
        when(chargeRepository.save(any(ChargeEntity.class))).thenReturn(chargeEntityMock);

        chargeService.save(chargeMock);

        verify(chargeRepository,times(1)).save(any(ChargeEntity.class));
        verify(chargeMapper, times(1)).mapDomainToEntity(any(Charge.class));
    }

    @Test
    public void whenSavePassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("charge is marked non-null but is null");
        chargeService.save(null);
    }
}
