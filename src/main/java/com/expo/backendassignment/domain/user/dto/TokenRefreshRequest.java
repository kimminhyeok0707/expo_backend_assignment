package com.expo.backendassignment.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Refresh Token 재발급 요청 본문을 표현하는 DTO입니다.
 */
@Getter
@NoArgsConstructor
public class TokenRefreshRequest {

	@NotBlank(message = "리프레시 토큰은 필수입니다.")
	private String refreshToken;
}
