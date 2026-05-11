package com.expo.backendassignment.domain.user.controller;

import com.expo.backendassignment.domain.user.dto.LoginRequest;
import com.expo.backendassignment.domain.user.dto.LoginResponse;
import com.expo.backendassignment.domain.user.dto.SignUpRequest;
import com.expo.backendassignment.domain.user.dto.UserResponse;
import com.expo.backendassignment.domain.user.service.AuthService;
import com.expo.backendassignment.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 관련 API 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

	private final AuthService authService;

	/**
	 * 회원가입을 처리합니다.
	 * 컨트롤러는 요청과 응답만 담당하고,
	 * 실제 비즈니스 로직은 서비스 계층으로 분리해 역할을 명확하게 유지합니다.
	 */
	@Operation(
		summary = "회원가입",
		description = "이메일 중복을 검사한 뒤 비밀번호를 암호화하여 사용자를 저장합니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "회원가입 요청 정보",
			required = true,
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SignUpRequest.class),
				examples = @ExampleObject(
					name = "회원가입 예시",
					value = """
						{
						  "email": "user@example.com",
						  "password": "Password123!",
						  "name": "김민혁"
						}
						"""
				)
			)
		),
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "회원가입 성공",
				content = @Content(schema = @Schema(implementation = ApiResponse.class))
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "이미 사용 중인 이메일"
			)
		}
	)
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
		UserResponse response = authService.signUp(request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success("USER_CREATED", "회원가입이 완료되었습니다.", response));
	}

	/**
	 * 로그인을 처리합니다.
	 * 컨트롤러는 요청과 응답만 담당하고,
	 * 사용자 조회, 비밀번호 검증, 토큰 발급과 저장은 서비스 계층에서 처리합니다.
	 */
	@Operation(
		summary = "로그인",
		description = "이메일과 비밀번호를 검증한 뒤 Access Token과 Refresh Token을 발급합니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "로그인 요청 정보",
			required = true,
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = LoginRequest.class),
				examples = @ExampleObject(
					name = "로그인 예시",
					value = """
							{
							   "email": "user12@example.com",
							  "password": "Password123!1"
							}
						"""
				)
			)
		),
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "로그인 성공",
				content = @Content(schema = @Schema(implementation = ApiResponse.class))
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "이메일 또는 비밀번호가 올바르지 않음"
			)
		}
	)
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
		LoginResponse response = authService.login(request);
		return ResponseEntity.ok(ApiResponse.success("LOGIN_SUCCESS", "로그인이 완료되었습니다.", response));
	}
}
