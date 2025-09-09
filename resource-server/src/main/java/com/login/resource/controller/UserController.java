package com.login.resource.controller;

import com.login.resource.repository.InMemoryUserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final InMemoryUserStore userStore;

    @GetMapping
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt jwt){
        String username = jwt.getSubject(); // 토큰에 들어있는 값 (id)
        var profile = userStore.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Map.of(
                "username", profile.username(),
                "email", profile.email()
        );
    }
}
