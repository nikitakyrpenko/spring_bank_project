package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.Role;
import com.epam.bankproject.bankproject.repository.UserRepository;
import com.epam.bankproject.bankproject.service.impl.UserServiceImpl;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImpTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String PASSWORD = "P@ssword97";
    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final String USER_EMAIL = "jondoe@gmail.com";
    private static final UserEntity userEntity;
    private static final User user = User.builder()
            .id(1)
            .name("Jon")
            .surname("Doe")
            .telephone("380984895779")
            .email("jondoe@gmail.com")
            .password(ENCODED_PASSWORD)
            .role(Role.ROLE_USER)
            .build();
    static {
        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Jon");
        userEntity.setSurname("Doe");
        userEntity.setTelephone("380984895779");
        userEntity.setEmail("jondoe@gmail.com");
        userEntity.setPassword(ENCODED_PASSWORD);
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setAccounts(Collections.emptyList());
    }

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Mapper<User, UserEntity> userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @After
    public void resetMocks() {
        reset(userRepository, passwordEncoder, userMapper);
    }

    @Test
    public void whenUserServiceLoginCorrect_thenLoginSuccessfully(){
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapEntityToDomain(userEntity)).thenReturn(user);
        when(passwordEncoder.matches(PASSWORD,user.getPassword())).thenReturn(true);

        User login = userService.login(USER_EMAIL, PASSWORD);

        assertEquals(user, login);
        verify(passwordEncoder).matches(eq(PASSWORD),eq(ENCODED_PASSWORD));
        verify(userRepository).findByEmail(eq(USER_EMAIL));

    }

    @Test
    public void whenUserServiceEmailNotExist_thenLoginThrowNoSuchElementException(){
        expectedException.expect(NoSuchElementException.class);
        expectedException.expectMessage("No value present");

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

        User login = userService.login(USER_EMAIL, PASSWORD);

        verifyNoInteractions(userMapper);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    public void whenUserServicePasswordDoNotMatch_thenLoginThrowNoSuchElementException(){
        expectedException.expect(NoSuchElementException.class);
        expectedException.expectMessage("No value present");

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(PASSWORD, userEntity.getPassword())).thenReturn(false);

        User login = userService.login(USER_EMAIL, PASSWORD);

        verifyNoInteractions(userMapper);
    }

    @Test
    public void whenUserServiceRegisterCorrect_thenRegisterSuccessfully(){
        User user = User.builder()
                .id(1)
                .name("Jon")
                .surname("Doe")
                .telephone("380984895779")
                .email("jondoe@gmail.com")
                .password(PASSWORD)
                .role(Role.ROLE_USER)
                .build();


        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userMapper.mapDomainToEntity(user)).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(any(UserEntity.class));

        userService.register(user);

        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).findByEmail(eq(userEntity.getEmail()));
        verify(userMapper).mapDomainToEntity(any(User.class));

    }
}
