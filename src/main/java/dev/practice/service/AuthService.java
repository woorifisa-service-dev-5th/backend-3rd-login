package dev.practice.service;

import dev.practice.dto.TokenResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

@Service
public class AuthService {
    private WebClient webClient;

    @Value("${oauth.client.id}")
    private String clientId;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    @Value("${oauth.client.redirecturi}")
    private String redirectUri;

    @Value("${oauth.tokenurl}")
    private String tokenUrl;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<TokenResponseDto> getTokens(String code) {
        // HTTP 요청 본문(body) 설정
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        // WebClient를 사용하여 POST 요청 보내기
        Function<UriBuilder, URI> tokenUri;
        return webClient.post()
                .uri(tokenUrl) // 요청 보낼 URI
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(formData) // 요청 본문 데이터 설정
                .retrieve() // 응답을 받기 시작
                .bodyToMono(TokenResponseDto.class); // 응답 본문을 TokenResponseDto 객체로 변환
    }
}
