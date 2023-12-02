package com.proj.twitter.controller;


import com.proj.twitter.dto.JwtUser;
import com.proj.twitter.entities.Follows;
import com.proj.twitter.entities.Tweet;
import com.proj.twitter.entities.User;
import com.proj.twitter.exceptions.UnAuthenticatedException;
import com.proj.twitter.service.FollowService;
import com.proj.twitter.service.UserService;
import graphql.GraphQLContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    GraphQLContext context;
    @QueryMapping
    public User getUserById(@Argument String id) {

        return userService.getUserById(id);
    }
    private JwtUser getUserFromContext(){
        JwtUser jwtUser = context.getOrDefault("user",null);
        if(jwtUser == null || jwtUser.getId() == null){
            throw new UnAuthenticatedException("Unauthenticated");
        }
        return jwtUser;
    }
    @QueryMapping
    public User getCurrentUser() {
        JwtUser jwtUser = getUserFromContext();
        log.info("cntr");
        return userService.getCurrentUser(jwtUser);
    }
    @QueryMapping
    public String verifyGoogleToken(@Argument String token) {
        return  userService.verifyGoogleToken(token);
    }


    @MutationMapping
    public Boolean followUser(@Argument String to){
        JwtUser jwtUser = getUserFromContext();
        log.info("cntr");
        return followService.followUser(jwtUser.getId(),to);
    }

    @MutationMapping
    public Boolean unFollowUser(@Argument String to){
        JwtUser jwtUser = getUserFromContext();
        return followService.unFollowUser(jwtUser.getId(), to);
    }

    @SchemaMapping
    public List<User> followers(User user)
    {
        return user.getFollowers().stream().map(Follows::getFollower).toList();
    }

    @SchemaMapping
    public List<User> following(User user)
    {
        return user.getFollowing().stream().map(Follows::getFollowing).toList();
    }

    @SchemaMapping
    public List<Tweet> tweets(User user)
    {
        return user.getTweets();
    }

    @SchemaMapping
    public List<User> recommendedUsers(User user)
    {
        JwtUser jwtUser = getUserFromContext();
        return userService.recommendUsers(jwtUser.getId());
    }

}

