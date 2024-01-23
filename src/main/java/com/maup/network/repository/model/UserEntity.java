package com.maup.network.repository.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String login;
    @Indexed(unique = true)
    private String email;
    private String password;

    private boolean deleted = false;
    private String publicName;
    private String bio;
}
