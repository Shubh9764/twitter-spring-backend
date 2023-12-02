package com.proj.twitter.controller;

import com.proj.twitter.dto.CreateTweetData;
import com.proj.twitter.dto.JwtUser;
import com.proj.twitter.entities.Tweet;
import com.proj.twitter.entities.User;
import com.proj.twitter.exceptions.UnAuthenticatedException;
import com.proj.twitter.service.TweetService;
import com.proj.twitter.service.UserService;
import graphql.GraphQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TweetController {

    @Autowired
    TweetService tweetService;
    @Autowired
    GraphQLContext context;

    @Autowired
    UserService userService;

    @QueryMapping
    public List<Tweet> getAllTweets(){
        return tweetService.getAllTweets();
    }
    @QueryMapping
    public String getSignedUrlForTweet(@Argument String imageName,@Argument String imageType){
        JwtUser jwtUser = context.getOrDefault("user",null);
        if(jwtUser == null || jwtUser.getId() == null){
            throw new UnAuthenticatedException("Unauthenticated");
        }
        return tweetService.getPreSignedUrl(jwtUser,imageName,imageType);
    }
    @SchemaMapping
    public User author(Tweet tweet){
         return tweet.getAuthor();
    }

    @MutationMapping
    public Tweet createTweet(@Argument CreateTweetData payload){
        JwtUser jwtUser = context.getOrDefault("user",null);
        if(jwtUser == null || jwtUser.getId() == null){
            throw new UnAuthenticatedException("Unauthenticated");
        }
        return tweetService.creatTweet(payload,jwtUser.getId());
    }

}
