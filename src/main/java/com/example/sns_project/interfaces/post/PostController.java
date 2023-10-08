package com.example.sns_project.interfaces.post;

import com.example.sns_project.interfaces.gateway.CommendGateway;
import com.example.sns_project.application.PostService;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.infra.auth.LoginUser;
import com.example.sns_project.infra.util.ResponseDto;
import com.example.sns_project.domain.post.entity.PostId;
import com.example.sns_project.interfaces.post.dto.PostCreate;
import com.example.sns_project.interfaces.post.dto.PostEdit;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
@RestController
public class PostController {
    private final PostService postService;
    private final CommendGateway commendGateway;

    public PostController(final PostService postService, final CommendGateway commendGateway) {
        this.postService = postService;
        this.commendGateway = commendGateway;
    }

    @PostMapping("/auth/posts")
    public ResponseEntity<ResponseDto<PostResponse>> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        commendGateway.request(postCreate, loginUser.getUser().getUserId());

        return ResponseEntity.ok(ResponseDto.success());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto<PostResponse>> get(@ModelAttribute PostId postId) {
        final PostResponse postResponse = postService.get(postId);

        return ResponseEntity.ok(ResponseDto.success(postResponse));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<List<PostResponse>>> getList(Pageable pageable) {
        final List<PostResponse> postList = postService.getList(pageable);

        return ResponseEntity.ok(ResponseDto.success(postList));
    }

    @PostMapping("/auth/posts/{postId}")
    public ResponseEntity<ResponseDto<String>> edit(@ModelAttribute PostId postId, @RequestBody PostEdit postEdit
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        postService.edit(postId, postEdit, loginUser.getUser().getUserId());

        return ResponseEntity.ok(ResponseDto.success());
    }

    @DeleteMapping("/auth/posts/{postId}")
    public ResponseEntity<ResponseDto<String>> delete(@ModelAttribute PostId postId, @AuthenticationPrincipal LoginUser loginUser) {
        postService.delete(postId, loginUser.getUser().getUserId());

        return ResponseEntity.ok(ResponseDto.success());
    }
}
