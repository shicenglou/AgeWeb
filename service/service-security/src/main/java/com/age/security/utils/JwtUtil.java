package com.age.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Slf4j
public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 24 * 60 * 60 *1000L;// 24 * 60 * 60 *1000  24个小时
    //设置秘钥明文
    public static final String JWT_KEY = "sangeng";

    public static String getUUID(){return UUID.randomUUID().toString().replace("-","");}

    public static String createJwt(String subject,Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm  = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        //当前时间加上固定有效期为合法时间
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        log.info("token 签发时间：{}",now);
        log.info("token 过期时间：{}",expDate);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)            // 主题  可以是JSON数据
                .setIssuedAt(now)
                .setIssuer("dph")
                .signWith(signatureAlgorithm,secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }


    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        log.info("解析token中！");
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    //2 根据token字符串得到用户信息
    public static String getUserInfoFromToken(String token) {
        SecretKey secretKey = generalKey();
        String userinfo = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return userinfo;
    }

    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjYWM2ZDVhZi1mNjVlLTQ0MDAtYjcxMi0zYWEwOGIyOTIwYjQiLCJzdWIiOiJzZyIsImlzcyI6InNnIiwiaWF0IjoxNjM4MTA2NzEyLCJleHAiOjE2MzgxMTAzMTJ9.JVsSbkP94wuczb4QryQbAke3ysBDIL5ou8fWsbt_ebg";
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }



}
