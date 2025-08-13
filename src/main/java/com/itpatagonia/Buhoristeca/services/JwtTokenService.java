package com.itpatagonia.Buhoristeca.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${apicecba.jwt.issuer}")
    private String issuer;

    @Value("${apicecba.jwt.audience}")
    private String audience;

    @Value("${apicecba.jwt.subject}")
    private String subject;

    @Value("${apicecba.jwt.secretKey}")
    private String cecbaSecretKey;

    @Value("${login.jwt.secretKey}")
    private String secretKey;

    @Value("${token.expiration.time}")
    private String tokenExpirationTime;

    @Value("${mobile.token.expiration.time}")
    private String mobileTokenExpirationTime;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public String generateToken() {
        return Jwts.builder()
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(tokenExpirationTime)))
                .signWith("key", signatureAlgorithm)
                .compact();
    }

}
