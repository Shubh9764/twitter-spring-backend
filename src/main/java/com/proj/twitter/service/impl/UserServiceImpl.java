package com.proj.twitter.service.impl;

import com.proj.twitter.dto.GoogleTokenResultDTO;
import com.proj.twitter.dto.JwtUser;
import com.proj.twitter.entities.Follows;
import com.proj.twitter.entities.User;
import com.proj.twitter.exceptions.ResourceNotFoundException;
import com.proj.twitter.repo.UserRepo;
import com.proj.twitter.service.UserService;
import com.proj.twitter.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public User getUserById(String id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
    }

    @Override
    public String verifyGoogleToken(String token) {
        try {
            String googleToken = token;
            String googleOAuthUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + googleToken;

            // Make a GET request to the Google OAuth endpoint
            RestTemplate restTemplate = new RestTemplate();
            GoogleTokenResultDTO data = restTemplate.getForObject(googleOAuthUrl, GoogleTokenResultDTO.class);

            User user = userRepo.findByEmail(data.getEmail()).orElse(null);

            // If user does not exist, create a new user
            System.out.println(user == null);
            if (user == null) {
                user = userRepo.save(new User(data.getEmail(), data.getGiven_name(), data.getFamily_name(), data.getPicture()));
            }

            // Generate a JWT token for the user
            return JwtHelper.generateTokenForUser(user);
        } catch (Exception e) {
            // Handle exceptions as needed
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public User getCurrentUser(JwtUser user) {

        return userRepo.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public List<User> recommendUsers(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        List<User> users = user.getFollowing().stream().map(Follows::getFollowing).toList();
        List<User> recommendedUsers = new ArrayList<>();
        for(User u : users){
            List<User> following = u.getFollowing().stream().map(Follows::getFollowing).toList();
            recommendedUsers.addAll(following);
        }
        return recommendedUsers;
    }

}
