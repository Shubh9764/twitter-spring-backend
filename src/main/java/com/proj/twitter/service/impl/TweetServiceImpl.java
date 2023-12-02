package com.proj.twitter.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.proj.twitter.dto.CreateTweetData;
import com.proj.twitter.dto.JwtUser;
import com.proj.twitter.entities.Tweet;
import com.proj.twitter.entities.User;
import com.proj.twitter.exceptions.ResourceNotFoundException;
import com.proj.twitter.exceptions.UnSupportedImageException;
import com.proj.twitter.repo.TweetRepo;
import com.proj.twitter.repo.UserRepo;
import com.proj.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {
    @Autowired
    private TweetRepo tweetRepo;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private UserRepo userRepo;

    @Value("${aws.s3.bucket}")
    private String s3Bucket;
    @Override
    public List<Tweet> getAllTweets() {
        return tweetRepo.findAll();
    }
    private  final  List<String> allowedImageTypes =  List.of(
            "image/jpg",
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/jfif"
    );

    @Override
    public String getPreSignedUrl(JwtUser jwtUser, String  imageName, String imageType) {

        if (!allowedImageTypes.contains(imageType))
            throw new UnSupportedImageException("Unsupported ImageType");
        String key = "uploads/"+ jwtUser.getId() +"/tweets/"+imageName+"-"+ LocalDate.now();
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(s3Bucket,key, HttpMethod.PUT);
        URL url = amazonS3.generatePresignedUrl(request);
        return url.toString();
    }

    @Override
    public Tweet creatTweet(CreateTweetData tweetData,String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User Found"));
        Tweet tweet = new Tweet();
        tweet.setContent(tweetData.getContent());
        tweet.setImageURL(tweetData.getImageURL());
        tweet.setAuthor(user);
        return tweetRepo.save(tweet);
    }
}
