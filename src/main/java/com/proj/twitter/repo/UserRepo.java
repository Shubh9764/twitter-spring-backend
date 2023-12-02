package com.proj.twitter.repo;

import com.proj.twitter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {
   Optional<User> findByEmail(String email);

}
