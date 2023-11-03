package io.proxyseller.tw.controller;

import io.proxyseller.tw.controller.dto.UserDto;
import io.proxyseller.tw.exception.GeneralException;
import io.proxyseller.tw.exception.NotFoundException;
import io.proxyseller.tw.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler
    public ResponseEntity<Void> notFoundException(NotFoundException _e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<Void> exception(GeneralException _e) {
        return ResponseEntity.badRequest().build();
    }
}
