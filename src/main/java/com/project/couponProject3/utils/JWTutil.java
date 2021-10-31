package com.project.couponProject3.utils;

import com.project.couponProject3.beans.UserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
/**
 * Class of JWT token creation
 */
public class JWTutil {

    //type of encryption algorithm
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    //our private key
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    //create our private key, in use when create the token
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), this.signatureAlgorithm);

    //generate key
    //we need user email, password and role for create the token
    //since the userDetail is an instance of class, we need to make it hashcode
    //the token need to get claims which is the information in hashcode

    /**
     * Create a JWT token for a client
     *
     * @param userDetails claims for creating the token
     * @return JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();           //create new hash map for claims
        claims.put("userId", userDetails.getId());              //insert id
        claims.put("clientType", userDetails.getClientType());  //insert user type (role)
        return createToken(claims, userDetails.getEmail());     //send the subject (email)
    }

    //we create the JWT token from the information that we got.
    private String createToken(Map<String, Object> claims, String email) { // String email=>subject
        Instant now = Instant.now();//get current time
        return
//                "Bearer " +
                Jwts.builder()       //create JWT builder to assist us with JWT creation
                        .setClaims(claims)  //set our data (claims-user,password,role,etc...)
                        .setSubject(email)  //set our subject, the first item that we will check
                        .setIssuedAt(Date.from(now))  //create issued at , which is current time
                        //expiration date, which after the date, we need to create a new token
                        .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                        .signWith(this.decodedSecretKey) //sign the token with our secret key
                        .compact();   //compact our token, that it will be smaller :)
    }

    /**
     * Get the token's claims
     *
     * @param token String
     * @return Claims
     * @throws ExpiredJwtException if can't return claims
     */
    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.decodedSecretKey)
                .build();
        String myToken = exposeToken(token);
        try {
            return jwtParser.parseClaimsJws(myToken).getBody();
        } catch (Exception err) {
            throw err;
        }
    }


    //        DecodedJWT jwt = JWT.decode(token);
//        JwkProvider provider = new UrlJwkProvider("http://localhost:4444");
//        Jwk jwk = provider.get(jwt.getKeyId());
//        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
//        algorithm.verify(jwt);
//
//// Check expiration
//        if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
//            throw new RuntimeException("Exired token!");
//        }


    /**
     * Get the token's email
     *
     * @param token String
     * @return token's email
     */
    public String extractEmail(String token) {
        String myToken = exposeToken(token);
        return extractAllClaims(myToken).getSubject();
    }

    /**
     * Get the token's expiration date
     *
     * @param token String
     * @return token's expiration date
     */
    public Date extractExpirationDate(String token) {
        String myToken = exposeToken(token);
        return extractAllClaims(myToken).getExpiration();
    }

    /**
     * Check the token claims.
     *
     * @param token String
     * @return false if token is still valid, true if otherwise
     */
    public boolean isTokenExpired(String token) {
        String myToken = exposeToken(token);
        try {
            extractAllClaims(myToken);
            return false;
        } catch (ExpiredJwtException | SignatureException err) {
            return true;
        }
    }

    /**
     * Check the validation of the token by user email and token claims
     *
     * @param token String
     * @return true if user email is correct and token is not expired
     */
    public boolean validateToken(String token) {
        String myToken = exposeToken(token);
        return !isTokenExpired(myToken);
    }

    /**
     * Get the token include the prefix "Bearer" from client side and returns only the token
     *
     * @param token client's token
     * @return token without the prefix "Bearer"
     */
    private String exposeToken(String token) {
        if (token.contains("Bearer")) {
            String myToken[] = token.split(" ");
            return myToken[1];
        }
        return token;
    }

    /*
    public static void main(String[] args) {
        //create our instance of user that the token will be created for him.
        UserDetails admin = new UserDetails("admin@admin.com", 0, ClientType.ADMINISTRATOR);
        //use our new shiny JWTutil.
        JWTutil myUtil = new JWTutil();
        //generate a new token
        String myToken = myUtil.generateToken(admin);
        //print the token on screen to show to our mother.
        System.out.println("my new shiny token:\n" + myToken);
        //get our clamis :)
        System.out.println("our token body is:\n" + myUtil.extractAllClaims(myToken));
        System.out.println("and the winner is:\n" + myUtil.extractEmail(myToken));
    }

     */

}
