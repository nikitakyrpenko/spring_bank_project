package com.epam.bankproject.bankproject.mapper;

import com.epam.bankproject.bankproject.domain.impl.User;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.Role;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class UserMapperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private Mapper<User, UserEntity> userMapper;

    @Test
    public void whenMapEntityToDomain_thenReturnDomain(){

        User me = User.builder()
                .id(1)
                .name("Jon")
                .surname("Doe")
                .email("jondoe@gmail.com")
                .password("P@ssword97")
                .telephone("380508321899")
                .role(Role.CLIENT)
                .build();

        UserEntity meEntity = new UserEntity();
        meEntity.setId(1);
        meEntity.setName("Jon");
        meEntity.setSurname("Doe");
        meEntity.setEmail("jondoe@gmail.com");
        meEntity.setPassword("P@ssword97");
        meEntity.setTelephone("380508321899");
        meEntity.setRole(Role.CLIENT);

        User actualDomain = userMapper.mapEntityToDomain(meEntity);


        assertThat(actualDomain).isEqualToIgnoringGivenFields(me,"accounts");
    }

    @Test
    public void whenMapDomainToEntity_thenReturnEntity(){

        User me = User.builder()
                .id(1)
                .name("Jon")
                .surname("Doe")
                .email("jondoe@gmail.com")
                .password("P@ssword97")
                .telephone("380508321899")
                .role(Role.CLIENT)
                .build();

        UserEntity meEntity = new UserEntity();
        meEntity.setId(1);
        meEntity.setName("Jon");
        meEntity.setSurname("Doe");
        meEntity.setEmail("jondoe@gmail.com");
        meEntity.setPassword("P@ssword97");
        meEntity.setTelephone("380508321899");
        meEntity.setRole(Role.CLIENT);

        UserEntity actualEntity = userMapper.mapDomainToEntity(me);


        assertThat(actualEntity).isEqualToIgnoringGivenFields(meEntity, "accounts");
    }

    @Test
    public void whenMapDomainToEntityWithNullValue_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("domain is marked non-null but is null");
        userMapper.mapDomainToEntity(null);
    }

    @Test
    public void whenMapEntityToDomainWithNullValue_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("entity is marked non-null but is null");
        userMapper.mapEntityToDomain(null);
    }

}
