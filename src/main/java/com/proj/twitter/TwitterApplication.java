package com.proj.twitter;

import graphql.GraphQLContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:env-local.properties"},ignoreResourceNotFound = true)
public class TwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApplication.class, args);
	}
	@Bean
	GraphQLContext context(){
		return new GraphQLContext.Builder().build();
	}
}
