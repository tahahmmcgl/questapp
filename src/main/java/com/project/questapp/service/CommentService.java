package com.project.questapp.service;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.request.CommentCreateRequest;
import com.project.questapp.request.CommentUpdateRequest;

import java.util.List;
import java.util.Optional;

public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getCommentWithParam(Optional<Long> userID, Optional<Long> postId) {
        if (userID.isPresent()&&postId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userID.get(),postId.get());
        } else if (userID.isPresent()) {
            return commentRepository.findByUserId((userID.get()));
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        }else
            return commentRepository.findAll();
    }

    public Comment getCommentById(long commentId) {
        return (Comment) commentRepository.findById(commentId).orElse(null);
    }

    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        User user=userService.getOneUser(commentCreateRequest.getUserId());
        Post post=postService.getOnePostById(commentCreateRequest.getPostId());
        if (user!=null&&post!=null)
        {
            Comment commentToSave=new Comment();
            commentToSave.setId(commentCreateRequest.getId());
            commentToSave.setUser(user);
            commentToSave.setPost(post);
            commentToSave.setText(commentCreateRequest.getText());
            return (Comment) commentRepository.save(commentToSave);
        }
        else
            return null;
    }

    public Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment=commentRepository.findById(commentId);
        if (comment.isPresent())
        {
            Comment commentToUpdate=comment.get();
            commentToUpdate.setText(commentUpdateRequest.getText());
           return (Comment) commentRepository.save(commentToUpdate);

        }else
            return null;
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
