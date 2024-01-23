package com.maup.network.repository;

import com.maup.network.repository.model.CommentEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {
    List<CommentEntity> findAllByPostIdInOrderByPostIdAscCreatedAtDesc(List<String> postIds);
}
