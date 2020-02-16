package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.ChargeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChargeRepositoryTest {

    @Autowired
    private ChargeRepository chargeRepository;

    @Test
    public void whenFindAllByAccountId_thenReturnChargesIterable() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(10);

        List<ChargeEntity> charges = chargeRepository.findAllByAccountId(accountEntity.getId());

        charges
                .forEach(chargeEntity -> assertEquals(accountEntity.getId(), chargeEntity.getAccount().getId()));
    }

    @Test
    public void whenFindAllByAccountIdPageable_thenReturnChargesIterable() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(10);

        List<ChargeEntity> charges = chargeRepository.findAllByAccountId(accountEntity.getId(), PageRequest.of(0, 3));

        assertTrue(charges.size() <= 3);

        charges
                .forEach(chargeEntity -> assertEquals(accountEntity.getId(), chargeEntity.getAccount().getId()));
    }

}
