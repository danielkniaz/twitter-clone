package io.proxyseller.tw.service;

import io.proxyseller.tw.controller.dto.UserDto;
import io.proxyseller.tw.repository.UserRepository;
import io.proxyseller.tw.repository.model.UserEntity;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    private final UserRepository repository;

    public boolean isEmailExist(@NotNull String email) {
        return repository.findByEmail(email) != null;
    }

    public boolean isLoginExist(@NotNull String login) {
        return repository.findByLogin(login) != null;
    }

    public Map<String, String> validateChanges(@NotNull UserEntity user, @NotNull UserDto dto) {
        // no way to change login - it is prohibited
        if (!user.getLogin().equals(dto.getLogin())) {
            return Map.of(UserService.FIELD, "login");
        }
        if (!user.getEmail().equals(dto.getEmail())) {
            if (isEmailExist(dto.getEmail())) {
                return Map.of(UserService.FIELD, "email", UserService.VALUE, dto.getEmail());
            }
        }
        return Map.of();
    }
}
