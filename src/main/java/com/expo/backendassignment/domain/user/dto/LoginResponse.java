package com.expo.backendassignment.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 성공 시 토큰과 사용자 정보를 함께 전달하는 응답 DTO입니다.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

	private final String accessToken;
	private final String refreshToken;
	private final UserResponse user;

	/**
	 * 로그인 응답 형식을 일관되게 만들기 위한 정적 팩토리 메서드입니다.
	 */
	public static LoginResponse of(String accessToken, String refreshToken, UserResponse user) {
		return new LoginResponse(accessToken, refreshToken, user);
	}
}
