package com.study.Boardify.domain;

import com.study.Boardify.domain.base.BaseEntity;
import com.study.Boardify.dto.board.BoardDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    private BoardCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Like> likes;
    private Integer likeCnt;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> comments;
    private Integer commentCnt;

    @OneToOne(fetch = FetchType.LAZY)
    @Setter
    private UploadImage uploadImage;

    public void update(BoardDto dto){
        this.title = dto.getTitle();
        this.body = dto.getBody();
    }

    public void likeChange(Integer likeCnt){
        this.likeCnt = likeCnt;
    }

    public void commentChange(Integer commentCnt){
        this.commentCnt = commentCnt;
    }
}
