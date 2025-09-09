package dev.practice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // @RequestBody는 기본 생성자가 필요합니다.
public class AccessTokenRequestDto {
    private String token;
    // 나중에 다른 필드가 추가되어도 여기에 선언만 하면 됩니다.
}
