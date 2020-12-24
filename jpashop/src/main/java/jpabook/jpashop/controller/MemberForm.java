package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

//쓰기 위해서 이거 추가해야함
//https://stackoverflow.com/questions/62151650/import-javax-validation-constraints-notempty-not-working
//	implementation 'org.springframework.boot:spring-boot-starter-validation'
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}