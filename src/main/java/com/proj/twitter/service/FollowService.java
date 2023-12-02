package com.proj.twitter.service;

import com.proj.twitter.entities.User;

import java.util.List;

public interface FollowService {

    public boolean followUser(String followerId, String followingId);

    public boolean unFollowUser(String followerId, String followingId);

}
