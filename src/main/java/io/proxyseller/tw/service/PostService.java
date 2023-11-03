package io.proxyseller.tw.service;

import io.proxyseller.tw.controller.dto.PostDto;
import io.proxyseller.tw.exception.NotFoundException;
import io.proxyseller.tw.exception.SecurityAccessException;
import io.proxyseller.tw.repository.PostRepository;
import io.proxyseller.tw.repository.model.CommentEntity;
import io.proxyseller.tw.repository.model.PostEntity;
import io.proxyseller.tw.repository.model.SubscriptionEntity;
import io.proxyseller.tw.repository.model.UserEntity;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PostInfoService infoService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final CommentService commentService;

    public PostEntity create(@NotNull PostDto post) {
        if (!StringUtils.hasLength(post.getContent())) {
            throw new RuntimeException("content should be blank");
        }
        if (!Objects.equals(userService.getUserId(), post.getUserId())) {
            throw new SecurityAccessException();
        }
        PostEntity entity = new PostEntity()
                .setUserId(post.getUserId())
                .setContent(post.getContent())
                .setCreatedTime(Instant.now().toEpochMilli());
        return repository.save(entity);
    }

    public PostEntity update(@NotNull PostDto post) {
        if (!Objects.equals(userService.getUserId(), post.getUserId())) {
            throw new SecurityAccessException();
        }
        PostEntity entity = infoService.findById(post.getId()).orElse(null);
        if (entity == null || entity.isDeleted()) {
            throw new NotFoundException(post.getId());
        }
        return repository.save(entity.setContent(post.getContent()));
    }

    public PostEntity delete(@NotNull PostDto post) {
        if (!Objects.equals(userService.getUserId(), post.getUserId())) {
            throw new SecurityAccessException();
        }
        PostEntity entity = infoService.findById(post.getId()).orElse(null);
        if (entity == null) {
            throw new NotFoundException(post.getId());
        }
        if (!entity.isDeleted()) {
            return repository.save(entity.setDeleted(true));
        }
        return entity;
    }

    public PostEntity like(@NotNull String postId) {
        PostEntity entity = infoService.findById(postId).orElse(null);
        if (entity == null || entity.isDeleted()) {
            throw new NotFoundException(postId);
        }
        String userId = userService.getUserId();
        if (userId == null) {
            throw new SecurityAccessException();
        }
        Set<String> userIds = entity.getUserIdsLiked();
        if (!userIds.add(userId)) {
            userIds.remove(userId);
        }
        return repository.save(entity);
    }

    @NotNull
    public List<PostEntity> findRecentPosts() {
        List<String> authorIds = subscriptionService.findSubscriptions().stream().map(SubscriptionEntity::getAuthorId).toList();
        List<String> activeAuthorIds = userService.findActiveInList(authorIds).stream().map(UserEntity::getId).toList();
        return repository.findAllByUserIdInOrderByCreatedTimeDesc(activeAuthorIds);
    }

    @NotNull
    public List<PostEntity> findUserPosts(@NotNull String userId) {
        String currentUserId = userService.getUserId();
        if (currentUserId == null) {
            throw new SecurityAccessException();
        }
        return repository.findAllByUserIdInOrderByCreatedTimeDesc(List.of(userId));
    }

    @NotNull
    public List<PostDto> findOwnPostsEnriched() {
        String currentUserId = userService.getUserId();
        if (currentUserId == null) {
            throw new SecurityAccessException();
        }
        List<PostEntity> posts = repository.findAllByUserIdInOrderByCreatedTimeDesc(List.of(currentUserId));
        Map<String, List<CommentEntity>> comments = fetchActiveAuthorComments(posts.stream().map(PostEntity::getId).toList());
        return posts.stream().map(post -> PostDto.enriched(post, comments.getOrDefault(post.getId(), List.of()))).toList();
    }

    @NotNull
    public PostDto findPost(@NotNull String postId) {
        PostEntity entity = infoService.findById(postId).orElseThrow(() -> new NotFoundException(postId));
        Map<String, List<CommentEntity>> comments = fetchActiveAuthorComments(List.of(postId));
        return PostDto.enriched(entity, comments.get(postId));
    }

    @NotNull
    private Map<String, List<CommentEntity>> fetchActiveAuthorComments(@NotNull List<String> postIds) {
        List<CommentEntity> comments = commentService.findCommentsForPosts(postIds);
        Set<String> activeAuthorIds = userService.findActiveInList(
                        comments.stream().map(CommentEntity::getAuthorId).toList())
                .stream().map(UserEntity::getId).collect(Collectors.toSet());
        return comments.stream().filter(comment -> activeAuthorIds.contains(comment.getAuthorId()))
                .collect(Collectors.groupingBy(CommentEntity::getPostId));
    }
}
