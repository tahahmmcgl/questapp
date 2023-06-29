package com.project.questapp.controller;

import com.project.questapp.entities.Comment;
import com.project.questapp.request.CommentCreateRequest;
import com.project.questapp.request.CommentUpdateRequest;
import com.project.questapp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {


    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> GetComment(@RequestParam Optional<Long> userID,@RequestParam Optional<Long> postId){
        return commentService.getCommentWithParam(userID,postId);    }
    @GetMapping("/{commentId}")
    public Comment getCommentByID(@PathVariable long commentId){
        return commentService.getCommentById(commentId);
    }

    @PostMapping
    public Comment createComment(@RequestBody CommentCreateRequest commentCreateRequest)
    {
        return commentService.createComment(commentCreateRequest);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest commentUpdateRequest)
    {
        return commentService.updateComment(commentId,commentUpdateRequest);
    }
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteCommentById(commentId);
    }
}
