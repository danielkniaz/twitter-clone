package com.maup.network.service;

import com.maup.network.exception.GeneralException;
import com.maup.network.exception.NotFoundException;
import com.maup.network.exception.SecurityAccessException;
import com.maup.network.repository.SubscriptionRepository;
import com.maup.network.repository.model.SubscriptionEntity;
import com.maup.network.repository.model.UserEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository repository;
    private final UserService userService;

    public UserEntity subscribe(@NotNull String userId) {
        String currentUserId = userService.getUserId();
        UserEntity author = getAuthorAndRequesterWithValidation(userId, currentUserId);
        repository.save(new SubscriptionEntity().setAuthorId(userId).setRequesterId(currentUserId));
        return author;
    }

    public UserEntity unsubscribe(@NotNull String userId) {
        String currentUserId = userService.getUserId();
        UserEntity author = getAuthorAndRequesterWithValidation(userId, currentUserId);
        SubscriptionEntity entity = repository.findByAuthorIdAndRequesterId(userId, currentUserId);
        if (entity == null) {
            throw new NotFoundException(userId);
        }
        repository.delete(entity);
        return author;
    }

    @NotNull
    public List<SubscriptionEntity> findSubscriptions() {
        String currentUserId = userService.getUserId();
        if (!StringUtils.hasLength(currentUserId)) {
            throw new NotFoundException(null);
        }
        return repository.findAllByRequesterId(currentUserId);
    }

    @NotNull
    private UserEntity getAuthorAndRequesterWithValidation(@NotNull String userId, String currentUserId) {
        if (currentUserId == null) {
            throw new SecurityAccessException();
        }
        if (currentUserId.equals(userId)) {
            throw new GeneralException();
        }
        UserEntity author = userService.find(userId);
        if (author.isDeleted()) {
            throw new NotFoundException(userId);
        }
        return author;
    }
}
