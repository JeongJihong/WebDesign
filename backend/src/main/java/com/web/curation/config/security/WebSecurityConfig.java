package com.web.curation.config.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Log
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    //암호화에 필요한 PasswordEncoder를 Bean에 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser(User.withUsername("user").password("{noop}password").roles("USER"))
                .withUser(User.withUsername("admin").password("{noop}password").roles("USER", "ADMIN"));

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic().disable()//rest api만 고려하여 기본 설정 해지
                .csrf().disable() //csrf 보안 토큰 disable 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //토큰 기반 인증이니 세션 사용 안함
                .and()
                .authorizeRequests()//요청에 대한 사용 권한 체크
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        //jwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣음
    }

//    @Bean
//    public PasswordEncoder noOpPasswordEncoder(){
//        return noOpPasswordEncoder()
}
