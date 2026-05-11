package com.expo.backendassignment.domain.user.dto;

import com.expo.backendassignment.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 정보를 외부 응답 형식에 맞게 전달하는 DTO입니다.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

	private final Long id;
	private final String email;
	private final String name;
	private final String role;

	/**
	 * 엔티티를 직접 노출하지 않기 위해 응답 전용 DTO로 변환합니다.
	 */
	public static UserResponse from(User user) {
		return new UserResponse(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getRole().name()
		);
	}
}
