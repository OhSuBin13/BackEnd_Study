package com.study.Boardify.dto.comment;

import com.study.Boardify.domain.Board;
import com.study.Boardify.domain.Comment;
import com.study.Boardify.domain.User;
import lombok.Data;

@Data
public class CommentCreateRequest {

    private String body;

    public Comment toEntity(Board board, User user){
        return Comment.builder()
                .user(user)
                .board(board)
                .body(body)
                .build();
    }
}
