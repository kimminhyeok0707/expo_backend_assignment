package com.expo.backendassignment.global.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * JWT 생성과 검증에 필요한 설정값을 바인딩합니다.
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	@NotBlank
	@Size(min = 32)
	private String secret;

	@Positive
	private long accessTokenExpiration;

	@Positive
	private long refreshTokenExpiration;
}
