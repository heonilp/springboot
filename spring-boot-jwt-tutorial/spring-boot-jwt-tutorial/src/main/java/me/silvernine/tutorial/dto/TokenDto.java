package me.silvernine.tutorial.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//dto 패키지를 만들고 외부와의 데이터 통신에 사용할 3개의 클래스를 만들어 줍니다.
public class TokenDto {

    private String token;
}
