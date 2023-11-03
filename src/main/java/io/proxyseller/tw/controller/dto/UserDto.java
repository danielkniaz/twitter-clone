package io.proxyseller.tw.controller.dto;

import io.proxyseller.tw.repository.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto{
     private String id;
     private String login;
     private String email;
     private String password;
     private String publicName;
     private String bio;

     public static UserDto from(UserEntity user) {
         return new UserDto(user.getId(), user.getLogin(), user.getEmail(), null, user.getPublicName(), user.getBio());
     }
}
