package website.project.website.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {


    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("IU$S&39S%57!kYs@NcIU$S&39S%57!kYs@NcIU$S&39S%57!kYs@Nc".getBytes());
    private static final long EXPIRATION_MS = 3600000 * 48; // 48小时

    /**
     * 生成JWT
     * 1. 定义一个密钥（Secret Key），用于对JWT进行签名。
     * 2. 设置JWT的过期时间（Expiration Time），用于控制JWT的有效期。
     * 3. 设置JWT的主题（Subject），通常是用户的唯一标识符。
     * 4. 设置JWT的签发时间（Issued At），用于记录JWT的生成时间。
     * 5. 使用密钥对JWT进行签名，生成一个数字签名。
     * 6. 将JWT的头部、载荷和数字签名组合成一个完整的JWT字符串。
     * 7. 返回生成的JWT字符串。
     * @param userId
     * @return
     */
    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 解析验证JWT
     * @param token
     * @return
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
