package com.login.resource.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserStore {
    private final Map<String, UserProfile> users = new HashMap<>();

    public InMemoryUserStore() {
        users.put("김현진", new UserProfile("test1", "test1@example.com"));
        users.put("힘내라", new UserProfile("test2", "test2@example.com"));
    }

    public Optional<UserProfile> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public record UserProfile(String username, String email) {}
}
