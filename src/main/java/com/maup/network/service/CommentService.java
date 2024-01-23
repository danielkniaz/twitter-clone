package com.maup.network.service;

import com.maup.network.controller.dto.CommentDto;
import com.maup.network.exception.NotFoundException;
import com.maup.network.exception.SecurityAccessException;
import com.maup.network.repository.CommentRepository;
import com.maup.network.repository.model.CommentEntity;
import com.maup.network.repository.model.PostEntity;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final UserService userService;
    private final PostInfoService postInfoService;

    public PostEntity comment(@NotNull CommentDto comment) {
        String currentUserId = userService.getUserId();
        if (currentUserId == null) {
            throw new SecurityAccessException();
        }
        Optional<PostEntity> post = postInfoService.findById(comment.getPostId());
        if (post.isEmpty()) {
            throw new NotFoundException(comment.getPostId());
        }
        repository.save(new CommentEntity()
                .setAuthorId(currentUserId)
                .setContent(comment.getContent())
                .setPostId(comment.getPostId())
                .setCreatedAt(Instant.now().toEpochMilli()));
        return post.get();
    }

    public List<CommentEntity> findCommentsForPosts(@NotNull List<String> postIds) {
        return repository.findAllByPostIdInOrderByPostIdAscCreatedAtDesc(postIds);
    }
}
