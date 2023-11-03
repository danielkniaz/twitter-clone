package io.proxyseller.tw.repository;

import io.proxyseller.tw.repository.model.CommentEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {
    List<CommentEntity> findAllByPostIdInOrderByPostIdAscCreatedAtDesc(List<String> postIds);
}
