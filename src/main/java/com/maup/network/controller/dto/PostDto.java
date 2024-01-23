package com.maup.network.controller.dto;

import com.maup.network.repository.model.CommentEntity;
import com.maup.network.repository.model.PostEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {
    private String id;
    private String userId;
    private String content;
    private boolean deleted;
    private Long createdTime;
    private List<String> likes;
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
