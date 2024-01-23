package com.maup.network.controller.dto;

import lombok.Data;

@Data
public class CommentDto {
    private String postId;
    private String authorId;
    private String content;
}
