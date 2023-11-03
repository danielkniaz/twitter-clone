package io.proxyseller.tw.repository;

import io.proxyseller.tw.repository.model.UserEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByLogin(String login);
    UserEntity findByEmail(String email);
    List<UserEntity> findAllByIdInAndDeletedNot(List<String> userIds);
}
