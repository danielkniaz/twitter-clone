package io.proxyseller.tw.repository;

import io.proxyseller.tw.repository.model.PostEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
    List<PostEntity> findAllByUserIdInOrderByCreatedTimeDesc(List<String> userIds);
//    List<PostEntity> findAllByUserIdsLikedContaining(String id); //TODO: async job
}
