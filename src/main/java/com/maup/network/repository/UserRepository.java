package com.maup.network.repository;

import com.maup.network.repository.model.UserEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByLogin(String login);
    UserEntity findByEmail(String email);
    List<UserEntity> findAllByIdInAndDeletedFalse(List<String> userIds);
}
