package com.project.questapp.controller;

import com.project.questapp.entities.User;
import com.project.questapp.repos.UserRepository;
import com.project.questapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {


    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;



    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId)
    {
        return userService.getOneUser(userId);
    }

    @PostMapping
    public User createUser(@RequestBody User user)
    {
        return userService.saveUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId,@RequestBody User newUser){
        return userService.updateUser(newUser,userId);
    }
    @DeleteMapping("/{userId}")
    public boolean deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
