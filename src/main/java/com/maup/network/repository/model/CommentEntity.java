package com.maup.network.repository.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "comments")
@Accessors(chain = true)
public class CommentEntity {
    @Id
    private String id;
    private String postId;
    private String authorId;
    private String content;
    private Long createdAt;
}
