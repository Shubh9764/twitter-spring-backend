package com.proj.twitter.service;

import com.proj.twitter.dto.JwtUser;
import com.proj.twitter.entities.User;

import java.util.List;

public interface UserService {
    public User getUserById(String id);

    public String verifyGoogleToken(String token);

    public User getCurrentUser(JwtUser user);

    public List<User> recommendUsers(String userId);

}
