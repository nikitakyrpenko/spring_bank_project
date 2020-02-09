package com.epam.bankproject.bankproject.service.impl;

import com.epam.bankproject.bankproject.domain.impl.User;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.repository.UserRepository;
import com.epam.bankproject.bankproject.service.UserService;
import com.epam.bankproject.bankproject.service.exception.DuplicateEntityException;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    //private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Mapper<User, UserEntity> userMapper;


    @Override
    public User login(@NotNull String email, @NotNull String password) {
        return userRepository.findByEmail(email)
                .map(userEntity -> userMapper.mapEntityToDomain(userEntity))
                .orElseThrow();
    }

    @Override
    public void register(@NotNull User user) {
        userRepository.findById(user.getId())
                .ifPresent(userEntity -> {throw new DuplicateEntityException("User already exist");});

        /*User userWithHashedPassword = user.toBuilder()
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .build();
        userRepository.save(userMapper.mapDomainToEntity(userWithHashedPassword));*/
    }
}
