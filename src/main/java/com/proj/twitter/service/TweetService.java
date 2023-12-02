package com.proj.twitter.service;

import com.proj.twitter.dto.CreateTweetData;
import com.proj.twitter.dto.JwtUser;
import com.proj.twitter.entities.Tweet;
import com.proj.twitter.entities.User;

import java.util.List;

public interface TweetService {
    public List<Tweet> getAllTweets();

    public String getPreSignedUrl(JwtUser jwtUser, String  imageName, String imageType);

    public Tweet creatTweet(CreateTweetData tweetData,String userId);
}
