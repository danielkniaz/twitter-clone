package com.maup.network.controller;

import com.maup.network.controller.dto.UserDto;
import com.maup.network.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping("/{userId}")
    public ResponseEntity<UserDto> subscribe(@PathVariable String userId) {
        return ResponseEntity.ok(UserDto.from(service.subscribe(userId)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> unsubscribe(@PathVariable String userId) {
        return ResponseEntity.ok(UserDto.from(service.unsubscribe(userId)));
    }
}
