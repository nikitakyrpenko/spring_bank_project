package com.epam.bankproject.bankproject.repository;


import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.UserEntity;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class AccountRepositoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void whenFindAllByOwnerId_thenReturnAccountEntity(){
        UserEntity me = new UserEntity();
        me.setId(1);
        me.setName("Freya");
        me.setEmail("dolor.Donec@etmagnaPraesent.net");

        List<AccountEntity> allByOwnerId = accountRepository.findAllByOwnerId(me.getId());

        allByOwnerId
                .forEach(accountEntity -> assertEquals(accountEntity.getOwner().getEmail(), me.getEmail()));
    }

    @Test
    public void whenFindAllByOwnerIdPageable_thenReturnAccountEntity(){
        UserEntity me = new UserEntity();
        me.setId(1);
        me.setName("Freya");
        me.setEmail("dolor.Donec@etmagnaPraesent.net");

        List<AccountEntity> allByOwnerId = accountRepository.findAllByOwnerId(me.getId(), PageRequest.of(0,2));

        assertTrue(allByOwnerId.size() <= 2);

        allByOwnerId
                .forEach(accountEntity -> assertEquals(accountEntity.getOwner().getEmail(), me.getEmail()));
    }


    @Test
    public void whenFindByOwnerIdNotExist_thenReturnEmptyIterable(){
        UserEntity me = new UserEntity();
        me.setId(10000);
        me.setName("Freya");
        me.setEmail("dolor.Donec@etmagnaPraesent.net");

        List<AccountEntity> allByOwnerId = accountRepository.findAllByOwnerId(me.getId());

        assertTrue(allByOwnerId.isEmpty());
    }


}
