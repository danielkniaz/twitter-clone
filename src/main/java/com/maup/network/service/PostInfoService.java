package com.maup.network.service;

import com.maup.network.repository.PostRepository;
import com.maup.network.repository.model.PostEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostInfoService {
    private final PostRepository repository;

    public Optional<PostEntity> findById(@NotNull String postId) {
        return repository.findById(postId);
    }
}
