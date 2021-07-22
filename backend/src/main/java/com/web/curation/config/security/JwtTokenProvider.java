package com.web.curation.config.security;

import com.web.curation.model.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "webfirewood";

    //토큰 유효 시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    //객체를 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //Create JWT Token
    public String createToken(String userPK, List<String> roles){
        //jwt payload에 저장되는 정보 단위
        Claims claims = Jwts.claims().setSubject(userPK);
        //정보는 key value 쌍으로 저장
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) //만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                //사용할 암호화 알고리즘, 적용할 값
                .compact();
    }

    //JWT token에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        Authentication temp = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return temp;
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토근에서 회원 정보 추출
    public String getUserPk(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request의 Header에서 token 값을 가져옴
    public String resolveToken(HttpServletRequest request){
        System.out.println("request : " + request.getHeader("X-AUTH-TOKEN"));
        return request.getHeader("X-AUTH-TOKEN");
    }

    //Token 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }
}
