package me.silvernine.tutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

//@Entity는 Database Table과 1:1로 매핑되는 객체를 뜻합니다.
//@Table은 객체와 매핑되는 Database의 Table명을 지정하기 위해 사용합니다.
@Entity
@Table(name = "user")
@Getter
@Setter //롬복
@Builder //빌더
@AllArgsConstructor //생성자
@NoArgsConstructor
public class User {

   @JsonIgnore //@JsonIgnore는 서버에서 Json 응답을 생성할때 해당 필드는 ignore 하겠다는 의미입니다.
   @Id //@Id는 해당 필드가 Primary Key임을 의미
   @Column(name = "user_id") //@Column은 매핑되는 Database Column의 정보를 정의합니다.
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long userId;

   @Column(name = "username", length = 50, unique = true)
   private String username;

   @JsonIgnore
   @Column(name = "password", length = 100)
   private String password;

   @Column(name = "nickname", length = 50)
   private String nickname;

   @JsonIgnore
   @Column(name = "activated")
   private boolean activated;

//   @ManyToMany @JoinTable 부분은 쉽게 말해 User, Authority 테이블의 다대다 관계를 일대다,
//   다대일 관계의 조인 테이블로 정의합니다.
   @ManyToMany
   @JoinTable(
      name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
   private Set<Authority> authorities;
}
