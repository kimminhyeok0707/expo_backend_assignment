package com.expo.backendassignment.global.exception;

import com.expo.backendassignment.global.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 애플리케이션 전역 예외를 공통 API 응답 형식으로 변환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		return ResponseEntity.status(errorCode.getStatus())
			.body(ApiResponse.fail(errorCode.getCode(), exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception
	) {
		FieldError fieldError = exception.getBindingResult().getFieldError();
		String message = fieldError != null ? fieldError.getDefaultMessage() : ErrorCode.INVALID_INPUT_VALUE.getMessage();
		return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
			.body(ApiResponse.fail(ErrorCode.INVALID_INPUT_VALUE.getCode(), message));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(
		ConstraintViolationException exception
	) {
		return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
			.body(ApiResponse.fail(ErrorCode.INVALID_INPUT_VALUE.getCode(), exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception
	) {
		return ResponseEntity.status(ErrorCode.INVALID_TYPE_VALUE.getStatus())
			.body(ApiResponse.fail(ErrorCode.INVALID_TYPE_VALUE.getCode(), exception.getMessage()));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException exception) {
		return ResponseEntity.status(ErrorCode.ACCESS_DENIED.getStatus())
			.body(ApiResponse.fail(ErrorCode.ACCESS_DENIED.getCode(), ErrorCode.ACCESS_DENIED.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
		return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
			.body(ApiResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
	}
}
