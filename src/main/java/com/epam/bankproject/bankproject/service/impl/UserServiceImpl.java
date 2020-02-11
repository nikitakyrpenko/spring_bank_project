package com.epam.bankproject.bankproject.service.impl;

import com.epam.bankproject.bankproject.domain.impl.User;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.repository.UserRepository;
import com.epam.bankproject.bankproject.service.UserService;
import com.epam.bankproject.bankproject.service.exception.DuplicateEntityException;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Mapper<User, UserEntity> userMapper;


    @Override
    public User login(@NotNull String email, @NotNull String password) {
        return userRepository.findByEmail(email)
                .map(userMapper::mapEntityToDomain)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow();
    }

    @Override
    public void register(@NotNull User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(userEntity -> {throw new DuplicateEntityException("User already exist");});

        User userWithHashedPassword = user.toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        UserEntity userEntity = userMapper.mapDomainToEntity(userWithHashedPassword);
        userRepository.save(userEntity);
    }
}
