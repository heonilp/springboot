package me.silvernine.tutorial.repository;

import me.silvernine.tutorial.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//이전에 만들었던 User Entity에 매핑되는 Repository를 만들기 위해
// repository 패키지를 생성하고 UserRepository 인터페이스를 추가합니다.
public interface UserRepository extends JpaRepository<User, Long> {
   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByUsername(String username);
}
