package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.Role;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindAll_thenReturn10(){
        List<UserEntity> actual = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(actual.size(),10);
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
    public void whenFindByEmailParameterIsNull_thenThrowException(){
        //TODO
        userRepository.findByEmail(null);
    }
}
