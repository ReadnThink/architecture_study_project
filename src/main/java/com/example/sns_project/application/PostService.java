package com.example.sns_project.application;

import com.example.sns_project.infra.PostRepository;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostEdit;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.domain.post.dto.PostSearch;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.exception.PostNotFound;
import com.example.sns_project.infra.UserRepository;
import com.example.sns_project.domain.user.exception.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(final PostRepository postRepository, final UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponse write(PostCreate postCreate, final Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        final Post postNotValid = postCreate.toEntity();
        postNotValid.isValid();

        var post = postRepository.save(postNotValid);

        post.addUser(user);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public PostResponse get(final Long postId) {
        final Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit, Long userId) {
        var post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.isSameUser(userId);
        post.editPost(
                postEdit.title(),
                postEdit.content()
        );
    }

    @Transactional
    public void delete(final Long postId, Long userId) {
        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.isSameUser(userId);

        postRepository.delete(post);
    }

    private void validateUserExists(final Long userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
    }
}
