package me.silvernine.tutorial.config;

import me.silvernine.tutorial.jwt.JwtSecurityConfig;
import me.silvernine.tutorial.jwt.JwtAccessDeniedHandler;
import me.silvernine.tutorial.jwt.JwtAuthenticationEntryPoint;
import me.silvernine.tutorial.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
//@EnableWebSecurity 는 기본적인 Web 보안을 활성화 하겠다는 어노테이션이고,
// 추가적인 설정을 위해서 WebSecurityConfigurer을 implements하거나
// WebSecurityConfigurerAdapter를 extends하는 방법이 있습니다.
//여기서는 WebSecurityConfigurerAdapter을 extends하여 설정을 진행했습니다.

//@EnableGlobalMethodSecurity(prePostEnabled = true) 어노테이션은 메소드 단위로
//@PreAuthorize 검증 어노테이션을 사용하기 위해 추가합니다.
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    //위에서 만들었던 TokenProvider, JwtAuthenticationEntryPoint, JwtAccessDeniedHandler를 주입받는 코드를 추가합니다.
    //Password Encode는 BCryptPasswordEncoder()를 사용하겠습니다.
    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    //Password Encode는 BCryptPasswordEncoder()를 사용하겠습니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //h2-console 페이지의 접근은 Spring Security 관련 로직을 수행하지 않도록
    // configure(WebSecurity web)를 오버라이드한 메소드를 새롭게 추가합니다.
    //따라서 h2-console/하위 모든 요청, /favicon.ico에 대한 요청은 Spring Security 로직을 수행하지 않고 접근할 수 있게 됩니다.
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                        ,"/error"
                );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console 예외처리를 위해 만들었던 코드를 지정해줍니다.
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                //데이터 확인을 위해 사용하고 있는 h2-console을 위한 설정을 추가해줍니다.
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                ///api/hello, /api/authenticate, /api/signup 3가지 API는 Token이 없어도 호출할 수 있도록 허용합니다.
                .and()
                .authorizeRequests()
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/signup").permitAll()

                .anyRequest().authenticated()
                //위에서 만들었던 JwtFilter를 addFilterBefore 메소드로 등록했던 JwtSecurityConfig 클래스도 적용해줍니다.
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        //authorizeRequests()는 HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미이고,
        // antMatchers(path).permitAll() 는 해당 path로 들어오는 요청은 인증없이 접근을 허용하겠다는 의미 입니다.
        // .anyRequest().authenticated()는 나머지 요청은 모두 인증되어야 한다는 의미입니다.
    }
}