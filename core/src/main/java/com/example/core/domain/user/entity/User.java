package com.example.core.domain.user.entity;

import com.example.core.domain.post.entity.PostId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{
    @EmbeddedId
    @Column(name = "userId")
    private UserId userId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private UserRole userRole;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
    private PostId postId;
    public UserId getUserId() {
        return userId;
    }

    @Builder
    public User(UserId userId, final String name, final String email, final String password, final  UserRole userRole) {
        if (userId == null) {
            userId = new UserId();
        }
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void addPost(PostId postId) {
        this.postId = postId;
    }
}
