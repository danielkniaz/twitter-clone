package io.proxyseller.tw.controller;

import io.proxyseller.tw.controller.dto.UserDto;
import io.proxyseller.tw.exception.GeneralException;
import io.proxyseller.tw.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    //TODO: rm
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable String id) {
        return ResponseEntity.ok(UserDto.from(service.find(id)));
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findUsers() {
        return ResponseEntity.ok(service.find());
    }
    @GetMapping("/")
    public ResponseEntity<String> hw() {
        return ResponseEntity.ok("Hello world");
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(UserDto.from(service.register(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto user) {
        if (!user.getId().equals(id)) {
            throw new GeneralException();
        }
        return ResponseEntity.ok(UserDto.from(service.update(id, user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(UserDto.from(service.delete(id)));
    }
}
