package io.proxyseller.tw.controller.dto;

import lombok.Data;

@Data
public class CommentDto {
    private String postId;
    private String authorId;
    private String content;
}
