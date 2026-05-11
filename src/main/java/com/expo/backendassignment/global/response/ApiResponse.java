package com.expo.backendassignment.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 컨트롤러 응답에서 공통으로 사용하는 응답 래퍼입니다.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

	private final boolean success;
	private final String code;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T data;

	public static <T> ApiResponse<T> success(String code, String message, T data) {
		return new ApiResponse<>(true, code, message, data);
	}

	public static ApiResponse<Void> success(String code, String message) {
		return new ApiResponse<>(true, code, message, null);
	}

	public static ApiResponse<Void> fail(String code, String message) {
		return new ApiResponse<>(false, code, message, null);
	}
}
