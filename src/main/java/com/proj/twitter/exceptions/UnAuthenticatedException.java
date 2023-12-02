package com.proj.twitter.exceptions;

public class UnAuthenticatedException extends RuntimeException{

    public UnAuthenticatedException(String msg){
        super(msg);
    }
}
