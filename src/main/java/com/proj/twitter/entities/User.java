package com.proj.twitter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User1")
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String profileImageUrl;

    @Column(name = "createdAt")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

    @OneToMany(mappedBy = "following")
    private List<Follows> followers;

    @OneToMany(mappedBy = "follower")
    private List<Follows> following;

    public User(String email, String firstName, String lastName, String profileImageUrl) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImageUrl = profileImageUrl;
    }
}
