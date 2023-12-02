package com.proj.twitter.repo;

import com.proj.twitter.entities.Follows;
import com.proj.twitter.entities.FollowsId;
import com.proj.twitter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface FollowsRepo extends JpaRepository<Follows, FollowsId> {

   Optional<Follows> findByFollowerAndFollowing(User follower, User following);
   Optional<Follows> findByFollower(User follower);

}
