package com.maup.network.service;

import com.maup.network.exception.NotFoundException;
import com.maup.network.exception.UserRegistrationException;
import com.maup.network.controller.dto.UserDto;
import com.maup.network.exception.SecurityAccessException;
import com.maup.network.repository.UserRepository;
import com.maup.network.repository.model.UserEntity;
import com.maup.network.security.UserUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.hasLength;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    public static final String FIELD = "FIELD", VALUE = "VALUE";
    private static final String NOT_EMPTY = "cannot be blank";

    private final UserRepository repository;
    private final UserValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    public UserEntity find(@NotNull String id) {
        UserEntity user = repository.findById(id).orElse(null);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException(id);
        }
        return user;
    }

    public List<UserDto> find() {
        return repository.findAll().stream().filter(u -> !u.isDeleted()).map(UserDto::from).toList();
    }

    public String getUserId() {
        return Optional.ofNullable(repository.findByLogin(UserUtils.currentUserLogin()))
                .map(UserEntity::getId).orElse(null);
    }

    public UserEntity register(@NotNull UserDto user) {
        if (!hasLength(user.getLogin())) {
            throw new UserRegistrationException("login", NOT_EMPTY);
        }
        if (!hasLength(user.getEmail())) {
            throw new UserRegistrationException("email", NOT_EMPTY);
        }
        if (validationService.isLoginExist(user.getLogin())) {
            throw new UserRegistrationException("login", user.getLogin());
        }
        if (validationService.isEmailExist(user.getEmail())) {
            throw new UserRegistrationException("email", user.getEmail());
        }
        if (!hasLength(user.getPassword())) {
            throw new UserRegistrationException("password", NOT_EMPTY);
        }
        UserEntity entity = new UserEntity();
        entity.setLogin(user.getLogin());
        entity.setEmail(user.getEmail());
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setPublicName(user.getPublicName());
        entity.setBio(user.getBio());
        return repository.save(entity);
    }

    public UserEntity update(@NotNull String id, @NotNull UserDto updated) {
        UserEntity user = repository.findById(id).orElse(null);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException(id);
        }
        if (!UserUtils.currentUserLogin().equals(user.getLogin())) {
            throw new SecurityAccessException();
        }
        Map<String, String> errors = validationService.validateChanges(user, updated);
        if (!errors.isEmpty()) {
            throw new UserRegistrationException(errors.get(FIELD), errors.get(VALUE));
        }
        if (updateUserDetails(user, updated)) {
            user = repository.save(user);
        }
        return user;
    }

    public UserEntity delete(@NotNull String id) {
        UserEntity user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException(id);
        }
        if (!UserUtils.currentUserLogin().equals(user.getLogin())) {
            throw new SecurityAccessException();
        }
        if (!user.isDeleted()) {
            user.setDeleted(true);
            user = repository.save(user);
        }
        return user;
    }

    @NotNull
    public List<UserEntity> findActiveInList(@NotNull List<String> authorIds) {
        return repository.findAllByIdInAndDeletedFalse(authorIds);
    }

    private boolean updateUserDetails(@NotNull UserEntity user,@NotNull UserDto updated) {
        boolean result = false;
        if (!user.getBio().equals(updated.getBio())) {
            user.setBio(updated.getBio());
            result = true;
        }
        if (!user.getPublicName().equals(updated.getPublicName())) {
            user.setPublicName(updated.getPublicName());
            result = true;
        }
        if (!user.getEmail().equals(updated.getEmail())) {
            user.setEmail(updated.getEmail());
            result = true;
        }
        if (updated.getPassword() != null) {
            String encoded = passwordEncoder.encode(updated.getPassword());
            if (!user.getPassword().equals(encoded)) {
                user.setPassword(encoded);
                result = true;
            }
        }
        return result;
    }
}
