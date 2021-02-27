package me.silvernine.tutorial.controller;

import me.silvernine.tutorial.dto.UserDto;
import me.silvernine.tutorial.entity.User;
import me.silvernine.tutorial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
//controller 패키지에 UserService를 주입받는 UserController 클래스를 생성합니다.
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    //signup 메소드는 회원가입 API 이고 SecurityConfig.java 에서 permitAll를 설정했기 때문에 권한 없이 호출할 수 있습니다.
    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
/*
getMyUserInfo 메소드는 현재 Security Context에 저장되어 있는 인증 정보의 username을 기준으로 한
유저 정보 및 권한 정보를 리턴하는 API 입니다.
@PreAuthorize(“hasAnyRole(‘USER’,’ADMIN’)”) 어노테이션을 이용해서 ROLE_USER, ROLE_ADMIN 권한 모두 호출 가능하게 설정합니다.
getUserInfo 메소드는 username을 파라미터로 받아 해당 username의 유저 정보 및 권한 정보를 리턴합니다.
 @PreAuthorize(“hasAnyRole(‘ADMIN’)”) 어노테이션을 이용해서 ROLE_ADMIN 권한을 소유한 토큰만 호출할 수 있도록 설정합니다.
*/