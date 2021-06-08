package com.example.demo.security;

public final class SecurityConstant {
    public static final Integer EXPIRATION_TIME = 864_000_000;// 10 days
    public static final String SECRET_KEY = "secretKey";
    public static final String HEADER_STRING = "Authorization";
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String TOKEN_PREFIX ="" ;
    public static final String SIGN_UP_URL = "/api/user/create";
}
