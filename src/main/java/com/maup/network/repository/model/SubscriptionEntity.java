package com.maup.network.repository.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "subscriptions")
@Accessors(chain = true)
public class SubscriptionEntity {
    @Id
    private String id;
    private String authorId;
    private String requesterId;
}
