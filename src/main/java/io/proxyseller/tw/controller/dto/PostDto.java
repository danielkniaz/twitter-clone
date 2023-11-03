package io.proxyseller.tw.controller.dto;

import io.proxyseller.tw.repository.model.CommentEntity;
import io.proxyseller.tw.repository.model.PostEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {
    private String id;

    // FIXME: if user is deleted, need to set all his posts as deleted(?). On the other hand, they are not seen except commenting a post
    private String userId;
    private String content;
    private boolean deleted;
    private Long createdTime;

    // FIXME: if user is deleted, need to set all his likes as deleted in future - async job?
    private List<String> likes;

    // FIXME: if user is deleted, need to set all his comments as deleted in future - async job?
    private List<CommentDto> comments;

    public static PostDto from(PostEntity entity) {
        return new PostDto(entity.getId(), entity.getUserId(), entity.getContent(), entity.isDeleted(),
                entity.getCreatedTime(), new ArrayList<>(entity.getUserIdsLiked()), List.of());
    }

    public static PostDto enriched(PostEntity entity, List<CommentEntity> comments) {
        var result = from(entity);
        result.setComments(comments.stream().map(comment -> {
            var dto = new CommentDto();
            dto.setAuthorId(comment.getAuthorId());
            dto.setContent(comment.getContent());
            return dto;
        }).toList());
        return result;
    }
}
