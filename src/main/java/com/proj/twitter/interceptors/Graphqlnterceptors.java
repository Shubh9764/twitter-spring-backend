package com.proj.twitter.interceptors;

import com.proj.twitter.dto.JwtUser;
import com.proj.twitter.utils.JwtHelper;
import graphql.GraphQLContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class Graphqlnterceptors implements WebGraphQlInterceptor {
    @Autowired
    GraphQLContext context;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        log.info("interceptor");

        List<String> token = request.getHeaders().get("Authorization");
        if(token!=null && !token.isEmpty()){
            if(token.get(0) != null){
                log.info(token.get(0));
                if(token.get(0).startsWith("Bearer ")){
                    JwtUser jwtUser = JwtHelper.decodeToken(token.get(0).substring(7));
                    context.put("user",jwtUser);
                    log.info(jwtUser+"");
                    return chain.next(request);
                }
            }

        }
        context.delete("user");
        return chain.next(request);
    }
}
