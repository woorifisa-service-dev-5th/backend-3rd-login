package dev.practice.controller;

import dev.practice.common.ApiResponse;
import dev.practice.dto.AccessTokenRequestDto;
import dev.practice.dto.TokenResponseDto;
import dev.practice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${oauth.client.id}")
    private String clientId;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    @Value("${oauth.client.redirecturi}")
    private String redirectUri;

    @Value("${oauth.tokenurl}")
    private String tokenUrl;

    private final AuthService authService;

    @GetMapping("/url")
    public ResponseEntity<ApiResponse<?>> returnurl(){
        String url = "http://localhost:9000/oauth2/authorize" +
                "?response_type=code&client_id="+clientId+"&scope=openid read write&" +
                "redirect_uri="+redirectUri+"&client_secret="+clientSecret;
//        UrlDto urlDto = new UrlDto(url);
        return ResponseEntity.ok(ApiResponse.success("url", url));
    }

    @PostMapping("/getAccessToken")
    public Mono<ResponseEntity<ApiResponse<TokenResponseDto>>> getAccessToken(@RequestBody AccessTokenRequestDto requestDto){
        String code = requestDto.getToken(); // DTO에서 임시 코드 추출

        // AuthService를 호출하여 토큰을 받아옴
        return authService.getTokens(code)
                .map(tokens -> ResponseEntity.ok(ApiResponse.success("tokens", tokens))) // 성공 시
                .onErrorResume(error -> Mono.just( // 에러 발생 시
                        ResponseEntity.internalServerError().body(ApiResponse.fail(error.getMessage()))
                ));
    }


}
