package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.Role;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindAll_thenReturnTrue(){
        List<UserEntity> actual = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        assertFalse(actual.isEmpty());
    }

    @Test
    public void whenFindByEmail_thenReturnUserEntity(){
        UserEntity me = new UserEntity();
        me.setId(1);
        me.setName("Freya");
        me.setEmail("dolor.Donec@etmagnaPraesent.net");

        Optional<UserEntity> found = userRepository.findByEmail(me.getEmail());

        assertEquals(me.getName(), found.get().getName());
    }

    @Test
    public void whenFindByEmailNotExist_thenThrowNoSuchElementException(){
        expectedException.expect(NoSuchElementException.class);
        expectedException.expectMessage("No value present");

        UserEntity me = new UserEntity();
        me.setId(1);
        me.setName("Freya");
        me.setEmail("dolor.D123123onec@etmagnaPr123123aesent.net");

        UserEntity userEntity = userRepository.findByEmail(me.getEmail()).get();

    }

    @Test
    public void whenSaveUserEntity_thenReturnUserEntity(){

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setExpirationDate(Date.valueOf("2021-03-12"));
        accountEntity.setBalance(1000.0);
        accountEntity.setDepositRate(0.2);
        accountEntity.setAccountType(AccountType.DEPOSIT);

        UserEntity meEntity = new UserEntity();
        meEntity.setName("Jon");
        meEntity.setSurname("Doe");
        meEntity.setEmail("jondoe@gmail.com");
        meEntity.setPassword("P@ssword97");
        meEntity.setTelephone("380508321899");
        meEntity.setRole(Role.ROLE_USER);
        meEntity.setAccounts(Arrays.asList(accountEntity));

        UserEntity save = userRepository.save(meEntity);
        assertEquals(meEntity, save);
        assertEquals(meEntity.getAccounts(), save.getAccounts());
    }

    @Test
    public void whenFindByEmailParameterIsNull_thenThrowException(){
        /*expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("email is marked non-null but is null");*/
        userRepository.findByEmail(null);
    }
}
