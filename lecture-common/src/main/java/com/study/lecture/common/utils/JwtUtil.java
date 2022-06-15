package com.study.lecture.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * JWT工具类，生成或解析token
 * </p>
 * <br>
 * Creation Time: 2022/4/5 10:09
 *
 * @author zqc
 * @since 1.0
 */
public class JwtUtil {

    /**
     * 默认有效期
     * 24 * 60 * 60 *1000  一天
     */
    public static final Long JWT_TTL = 24 * 60 * 60 *1000L;

    /**
     * 设置密钥明文
     */
    public static final String JWT_KEY = "lecture123";

    /**
     * 设置签发者
     */
    public static final String JWT_ISSUE = "lecture123";

    /**
     * 获取uuid
     * @return uuid
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 根据uuid、数据生成token
     * @param subject token中要存放的数据（json格式）
     * @return jwt
     */
    public static String createJwt(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * 根据uuid、数据生成token
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return token
     */
    public static String createJwt(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 根据id、数据生成token
     * @param id 用户id
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token过期时间
     * @return token
     */
    public static String createJwt(String id, String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    /**
     * 构建jwt
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token过期时间
     * @param uuid uuid
     * @return
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis == null){
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        /*
          setId(uuid)                                // 设置唯一id
          setSubject(subject)                        // 主题 可以是JSON数据
          setIssuer(JWT_ISSUE)                       // 签发者
          setIssuedAt(now)                           // 签发时间
          signWith(signatureAlgorithm, secretKey)    // 使用HS256对称加密算法签名, 第二个参数为秘钥
         */
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer(JWT_ISSUE)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expDate);
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return 生成加密密钥
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析token
     *
     * @param jwt token
     * @return 解析后的对象
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjYWM2ZDVhZi1mNjVlLTQ0MDAtYjcxMi0zYWEwOGIyOTIwYjQiLCJzdWIiOiJzZyIsImlzcyI6InNnIiwiaWF0IjoxNjM4MTA2NzEyLCJleHAiOjE2MzgxMTAzMTJ9.JVsSbkP94wuczb4QryQbAke3ysBDIL5ou8fWsbt_ebg";
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }
}
