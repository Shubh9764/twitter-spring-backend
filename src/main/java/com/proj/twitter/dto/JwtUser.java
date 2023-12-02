package com.proj.twitter.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtUser {
    private String id;
    private String email;
}
