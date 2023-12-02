package com.proj.twitter.service.impl;

import com.proj.twitter.entities.Follows;
import com.proj.twitter.entities.User;
import com.proj.twitter.repo.FollowsRepo;
import com.proj.twitter.repo.UserRepo;
import com.proj.twitter.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowsRepo followsRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public boolean followUser(String followerId, String followingId) {
        Optional<User> followerOpt = userRepo.findById(followerId);
        Optional<User> followingOpt = userRepo.findById(followingId);

        if (followerOpt.isPresent() && followingOpt.isPresent()) {
            Follows follow = new Follows();
            follow.setFollower(followerOpt.get());
            follow.setFollowing(followingOpt.get());
            followsRepo.save(follow);
            log.info("true");
            return true;
        }

        log.info("followUser false");
        return false;
    }

    @Override
    public boolean unFollowUser(String followerId, String followingId) {
        Optional<User> followerOpt = userRepo.findById(followerId);
        Optional<User> followingOpt = userRepo.findById(followingId);

        if (followerOpt.isPresent() && followingOpt.isPresent()) {
            Follows follow = followsRepo.findByFollowerAndFollowing(followerOpt.get(), followingOpt.get()).orElse(null);
            if (follow != null) {
                followsRepo.delete(follow);
                return true;
            }
        }

        return false;
    }
}
