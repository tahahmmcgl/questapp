package com.project.questapp.controller;

import com.project.questapp.entities.Post;
import com.project.questapp.request.PostCreateRequest;
import com.project.questapp.request.PostUpdateRequest;
import com.project.questapp.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    public PostController(PostService postService) {
        this.postService = postService;
    }

    private PostService postService;

    @GetMapping
    public List<Post> getPosts(@RequestParam Optional<Long> userId)
    {
        return postService.getPost(userId);
    }
    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable Long postId)
    {
        return postService.getOnePostById(postId);
    }

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest newpostRequest)
    {
        return postService.createPost(newpostRequest);
    }
    @PutMapping("/{postId}")
    public Post updatePostById(@PathVariable Long postId,@RequestBody PostUpdateRequest updateRequest)
    {
        return postService.updatePost(postId,updateRequest);
    }
    @DeleteMapping("/{postId}")
    public boolean deletePostById(@PathVariable Long postId)
    {
        return postService.deletePostById(postId);
    }
}
