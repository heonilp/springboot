package me.silvernine.tutorial.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
//util 패키지를 생성한 후 SecurityUtil 클래스를 생성합니다.
public class SecurityUtil {

   private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

   //getCurrentUsername() 메소드는 JwtFilter 클래스의 doFilter 메소드에서 저장한 Security Context의
   // 인증 정보에서 username을 리턴합니다.
   private SecurityUtil() {
   }

   //UserRepository, PasswordEncoder를 주입받는 UserService 클래스를 생성합니다.
   public static Optional<String> getCurrentUsername() {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null) {
         logger.debug("Security Context에 인증 정보가 없습니다.");
         return Optional.empty();
      }

      String username = null;
      if (authentication.getPrincipal() instanceof UserDetails) {
         UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
         username = springSecurityUser.getUsername();
      } else if (authentication.getPrincipal() instanceof String) {
         username = (String) authentication.getPrincipal();
      }

      return Optional.ofNullable(username);
   }
}
