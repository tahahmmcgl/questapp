package com.project.questapp.controller;

import com.project.questapp.entities.User;
import com.project.questapp.request.UserRequest;
import com.project.questapp.security.JwtTokenProvider;
import com.project.questapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;

        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login (@RequestBody UserRequest userRequest){
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userRequest.getUsername(),userRequest.getPassword());
        Authentication auth=authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken=jwtTokenProvider.generateJwtToken(auth);
        return "Bearer"+ jwtToken;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest register){
        if(userService.getOneUserByUserName(register.getUsername())!=null){
            return new ResponseEntity<>("Username alredy use", HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setUserName(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity<>("User Created",HttpStatus.CREATED);
    }

}
