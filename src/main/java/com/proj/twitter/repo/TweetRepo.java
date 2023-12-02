package com.proj.twitter.repo;

import com.proj.twitter.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepo extends JpaRepository<Tweet,Long> {
}
