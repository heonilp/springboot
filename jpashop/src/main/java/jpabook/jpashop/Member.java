package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter  @Setter
public class Member {
    @Id @GeneratedValue 
    //테이블
    private Long id;
    private String username;

}