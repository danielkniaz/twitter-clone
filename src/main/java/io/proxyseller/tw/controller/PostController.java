package io.proxyseller.tw.controller;

import io.proxyseller.tw.controller.dto.PostDto;
import io.proxyseller.tw.exception.GeneralException;
import io.proxyseller.tw.exception.NotFoundException;
import io.proxyseller.tw.exception.UserRegistrationException;
import io.proxyseller.tw.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping("/new")
    public ResponseEntity<PostDto> create(@RequestBody PostDto post) {
        return ResponseEntity.ok(PostDto.from(service.create(post)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@PathVariable String id, @RequestBody PostDto dto) {
        if (!dto.getId().equals(id)) {
            throw new GeneralException();
        }
        return ResponseEntity.ok(PostDto.from(service.update(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> delete(@PathVariable String id, @RequestBody PostDto dto) {
        if (!dto.getId().equals(id)) {
            throw new GeneralException();
        }
        return ResponseEntity.ok(PostDto.from(service.delete(dto)));
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<PostDto> like(@PathVariable String postId) {
        return ResponseEntity.ok(PostDto.from(service.like(postId)));
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> findSubscribed(){
        return ResponseEntity.ok(service.findRecentPosts().stream().map(PostDto::from).toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDto>> findUserPosts(@PathVariable String userId) {
        return ResponseEntity.ok(service.findUserPosts(userId).stream().map(PostDto::from).toList());
    }

    @GetMapping("/my")
    public ResponseEntity<List<PostDto>> findMyPosts() {
        return ResponseEntity.ok(
                service.findOwnPostsEnriched());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findPost(@RequestParam String postId) {
        return ResponseEntity.ok(service.findPost(postId));
    }


    @ExceptionHandler
    public ResponseEntity<Void> notFoundException(NotFoundException _e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<Void> registrationException(UserRegistrationException _e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler
    public ResponseEntity<Void> exception(GeneralException _e) {
        return ResponseEntity.badRequest().build();
    }
}
