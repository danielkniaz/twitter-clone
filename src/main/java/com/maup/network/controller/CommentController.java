package com.maup.network.controller;

import com.maup.network.controller.dto.CommentDto;
import com.maup.network.controller.dto.PostDto;
import com.maup.network.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @PostMapping("/comment")
    public ResponseEntity<PostDto> commentPost(@RequestBody CommentDto comment) {
        return ResponseEntity.ok(PostDto.from(service.comment(comment)));
    }
}
