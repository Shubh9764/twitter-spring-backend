package com.proj.twitter.exceptions;


import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;


@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if(ex instanceof  UnAuthenticatedException){
            return graphQLError(ex.getMessage(),ErrorType.UNAUTHORIZED,env);
        }
        if(ex instanceof ResourceNotFoundException){
            return graphQLError(ex.getMessage(),ErrorType.NOT_FOUND,env);
        }
        if(ex instanceof UnSupportedImageException){
            return graphQLError(ex.getMessage(),ErrorType.BAD_REQUEST,env);
        }
        return null;
    }

    private GraphQLError graphQLError(String msg,ErrorType errorType,DataFetchingEnvironment env){
        return GraphQLError.newError().errorType(errorType)
                .message(msg)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }






}
