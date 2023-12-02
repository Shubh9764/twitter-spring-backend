package com.proj.twitter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "follows1")
@IdClass(FollowsId.class)
public class Follows {

    @Id
    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    private User follower;

    @Id
    @ManyToOne
    @JoinColumn(name = "following_id", referencedColumnName = "id")
    private User following;


}

