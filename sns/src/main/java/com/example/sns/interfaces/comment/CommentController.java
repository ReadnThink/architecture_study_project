package com.example.sns.interfaces.comment;

import com.example.core.config.messaging.gateway.CommendGateway;
import com.example.core.domain.comment.dto.CommentCreate;
import com.example.core.domain.comment.dto.CommentEdit;
import com.example.core.domain.comment.entity.CommentId;
import com.example.core.domain.messaging.command.comment.KafkaCommentCreate;
import com.example.core.domain.messaging.command.comment.KafkaCommentDelete;
import com.example.core.domain.messaging.command.comment.KafkaCommentEdit;
import com.example.core.domain.post.entity.PostId;
import com.example.core.infra.auth.LoginUser;
import com.example.core.infra.util.ResponseDto;
import com.example.sns.application.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommendGateway commendGateway;

    public CommentController(final CommentService commentService, final CommendGateway commendGateway) {
        this.commentService = commentService;
        this.commendGateway = commendGateway;
    }
    // todo StatusCode 상황에 맞게 설계하기
    @PostMapping("/auth/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<String>> comment(@ModelAttribute final PostId postId, @RequestBody @Valid CommentCreate commentCreate, BindingResult bindingResult
            , @AuthenticationPrincipal LoginUser loginUser)
    {
//        commendGateway.request(commentCreate, loginUser.getUser().getUserId(), postId);
        commendGateway.request(new KafkaCommentCreate(commentCreate, loginUser.getUser().getUserId(), postId));

        return ResponseEntity.ok(ResponseDto.success());
    }

//    @GetMapping("/comments/{commentId}")
//    public ResponseEntity<ResponseDto<CommentResponse>> getComment(@ModelAttribute final CommentId commentId){
//        final CommentResponse comment = commentService.getComment(commentId);
//
//        return ResponseEntity.ok(ResponseDto.success(comment));
//    }
//
//    // todo 전체 댓글 리스트가 아닌 특정 댓글 리스트 API 필요
//    @GetMapping("/comments")
//    public ResponseEntity<ResponseDto<List<CommentResponse>>> commentList(Pageable pageable){
//        final List<CommentResponse> commentResponseList = commentService.getList(pageable);
//
//        return ResponseEntity.ok(ResponseDto.success(commentResponseList));
//    }

    @PostMapping("/auth/comments/{commentId}")
    public ResponseEntity<ResponseDto<String>> edit(@ModelAttribute CommentId commentId, @RequestBody CommentEdit commentEdit, @AuthenticationPrincipal LoginUser loginUser) {
//        final String success = commentService.edit(commentId, commentEdit, loginUser.getUser().getUserId());
        commendGateway.request(new KafkaCommentEdit(commentId, commentEdit, loginUser.getUser().getUserId()));

        return ResponseEntity.ok(ResponseDto.success());
    }

    @DeleteMapping("/auth/comments/{commentId}")
    public ResponseEntity<ResponseDto<String>> delete(@ModelAttribute CommentId commentId, @AuthenticationPrincipal LoginUser loginUser) {
//        final String success = commentService.delete(commentId, loginUser.getUser().getUserId());
        commendGateway.request(new KafkaCommentDelete(commentId, loginUser.getUser().getUserId()));

        return ResponseEntity.ok(ResponseDto.success());
    }
}
