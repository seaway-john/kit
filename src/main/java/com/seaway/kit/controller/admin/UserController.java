package com.seaway.kit.controller.admin;

import com.seaway.kit.config.Constants;
import com.seaway.kit.config.jwt.JwtLoginFilter;
import com.seaway.kit.pojo.mongo.Users;
import com.seaway.kit.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/register")
    public Mono<Void> register(@RequestBody Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.add(user);
    }

    @GetMapping("/refresh")
    public Mono<Users> getByUsername(@RequestParam("username") String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/token/refresh")
    public Mono<Void> getByUsername(Principal principal, HttpServletResponse response) {
        String token = JwtLoginFilter.getToken(principal.getName());
        response.addHeader(Constants.JWT_HEADER, token);

        return Mono.empty();
    }

}
