package com.expo.backendassignment.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Refresh Token 재발급 결과를 전달하는 응답 DTO입니다.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRefreshResponse {

	private final String accessToken;
	private final String refreshToken;

	/**
	 * 재발급 응답 형식을 일관되게 만들기 위한 정적 팩토리 메서드입니다.
	 */
	public static TokenRefreshResponse of(String accessToken, String refreshToken) {
		return new TokenRefreshResponse(accessToken, refreshToken);
	}
}
