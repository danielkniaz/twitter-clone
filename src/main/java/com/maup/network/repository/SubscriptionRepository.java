package com.maup.network.repository;

import com.maup.network.repository.model.SubscriptionEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends MongoRepository<SubscriptionEntity, String> {
    SubscriptionEntity findByAuthorIdAndRequesterId(String authorId, String requesterId);
    List<SubscriptionEntity> findAllByRequesterId(String requesterId);
}
