package com.example.todolistii.security;

import java.util.Date;

public class TokenManager {
    private String secretKey;

    public TokenManager(String secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(TokenPayload tokenPayload) {
        String mixedPayLoad = createMixedTokenPaylpad(tokenPayload);
        String signature = createSignature(mixedPayLoad);
        return String.format("%s#%s", mixedPayLoad, signature);
    }

    private String createMixedTokenPaylpad(TokenPayload tokenPayload) {
        String id = String.valueOf(tokenPayload.getUserId());
        String email = tokenPayload.getEmail();
        String created = String.valueOf(tokenPayload.getCreated().getTime());

        return String.format("%s#%s#%s", id, email,created);
    }

    private String createSignature(String mixedPayload) {
        // просто для учебного примера как-то скомпоновали
        return ""
                + mixedPayload.charAt(0)
                + mixedPayload.charAt(2)
                + secretKey.charAt(0)
                + secretKey.charAt(2)
                + mixedPayload.charAt(mixedPayload.length() - 1)
                + secretKey.charAt(secretKey.length() - 1);
    }

    public boolean verifyToken(String token) {
        TokenPayload payload = extractPayload(token);
        String trustedToken = createToken(payload);
        return token.equals(trustedToken);
    }

    public TokenPayload extractPayload(String token) {
        String[] tokenParts = token.split("#");
        long id = Long.parseLong(tokenParts[0]);
        String email = tokenParts[1];
        Date created = new Date(Long.parseLong(tokenParts[2]));

        return new TokenPayload(id, email, created);
    }
}
