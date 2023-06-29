package com.project.questapp.service;

import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.request.PostCreateRequest;
import com.project.questapp.request.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }
    public List<Post> getPost(Optional<Long> userId)
    {
        if (userId.isPresent())
            return postRepository.findByUserId(userId.get());

        return postRepository.findAll();
    }

    public Post getOnePostById(Long postId) {
        return (Post) postRepository.findById(postId).orElse(null);
    }

    public Post createPost(PostCreateRequest newPostRequest) {
        User user= userService.getOneUser(newPostRequest.getUserId());
        if (user==null)
            return null;
        Post toSave=new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        return (Post) postRepository.save(toSave);
    }

    public Post updatePost(Long postId, PostUpdateRequest updatePost) {
        Optional<Post> post=postRepository.findById(postId);
        if (post.isPresent())
        {
            Post toUpdate=post.get();
            toUpdate.setText(updatePost.getText());
            toUpdate.setTitle(updatePost.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public boolean deletePostById(Long postId) {
        Optional<Post> post=postRepository.findById(postId);
        if (post.isPresent())
        {
            postRepository.deleteById(postId);
            return true;
        }else
            return false;
    }
}
