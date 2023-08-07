package com.example.sns_project.domain.post;

import com.example.sns_project.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.aspectj.lang.annotation.Aspect;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String title;

    @Builder
    public Post(final Long id, final String content, final String title) {
        this.id = id;
        this.content = content;
        this.title = title;
    }

    public void change(String title, String content) {
        this.title = title != null ? title : this.title;
        this.content = content != null ? content : this.content;
    }
}
