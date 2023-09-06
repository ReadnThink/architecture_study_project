package com.example.sns_project.domain.comment.entity;

import com.example.sns_project.config.util.BanWords;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.config.exception.InvalidRequest;
import com.example.sns_project.domain.user.entity.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Arrays;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @EmbeddedId
    private CommentId id;

    private String author;

    private String content;

    @ManyToOne
    @JoinColumn
    private Post post;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Builder
    public Comment(CommentId id, final String content, final String author, final Post post) {
        if (id == null) {
            id = new CommentId();
        }
        this.id = id;
        this.content = content;
        this.author = author;
        this.post = post;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
        isValid();
    }

    public void isValid() {
        if (Arrays.stream(BanWords.values())
                .anyMatch(v -> v.getValue().contains(this.content))) {
            throw new InvalidRequest();
        }
    }

    public void isValid(String content) {
        if (Arrays.stream(BanWords.values())
                .anyMatch(v -> v.getValue().contains(this.content))) {
            throw new InvalidRequest();
        }
    }

    public boolean isSameUser(final UserId userId) {
        isValid();
        return this.post.getUser().getId().equals(userId);
    }

    public void editComment(final String comment) {
        isValid(comment);
        this.content = comment;
        this.lastModifiedAt = LocalDateTime.now();
    }
}

