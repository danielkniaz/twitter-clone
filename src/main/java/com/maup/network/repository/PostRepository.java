package com.maup.network.repository;

import com.maup.network.repository.model.PostEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
    List<PostEntity> findAllByUserIdInOrderByCreatedTimeDesc(List<String> userIds);
}
