package com.maup.network.repository.model;

import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "posts")
@Accessors(chain = true)
public class PostEntity {
    @Id
    private String id;
    private String userId;
    private String content;
    private boolean deleted = false;
    private Long createdTime;
    private Set<String> userIdsLiked;
}
