package com.expo.backendassignment.global.exception;

import lombok.Getter;

/**
 * 공통 API 예외 응답으로 변환하기 위해 {@link ErrorCode}를 담는 런타임 예외입니다.
 */
@Getter
public class CustomException extends RuntimeException {

	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public CustomException(ErrorCode errorCode, String detailMessage) {
		super(detailMessage);
		this.errorCode = errorCode;
	}
}
