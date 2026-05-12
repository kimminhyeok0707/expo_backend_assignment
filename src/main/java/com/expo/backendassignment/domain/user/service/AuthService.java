package com.expo.backendassignment.domain.user.service;

import com.expo.backendassignment.domain.user.Role;
import com.expo.backendassignment.domain.user.User;
import com.expo.backendassignment.domain.user.dto.LoginRequest;
import com.expo.backendassignment.domain.user.dto.LoginResponse;
import com.expo.backendassignment.domain.user.dto.SignUpRequest;
import com.expo.backendassignment.domain.user.dto.UserResponse;
import com.expo.backendassignment.domain.user.repository.UserRepository;
import com.expo.backendassignment.global.config.JwtTokenProvider;
import com.expo.backendassignment.global.exception.CustomException;
import com.expo.backendassignment.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인증 도메인의 비즈니스 로직을 담당하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 회원가입을 처리하고 응답 전용 DTO를 반환합니다.
	 * 비밀번호는 원문 노출을 막기 위해 반드시 암호화해서 저장하고,
	 * 엔티티는 내부 모델이므로 응답에는 DTO로 변환한 데이터만 반환합니다.
	 */
	@Transactional
	public UserResponse signUp(SignUpRequest request) {
		validateDuplicatedEmail(request.getEmail());

		User user = new User(
			request.getEmail(),
			passwordEncoder.encode(request.getPassword()),
			request.getName(),
			Role.user
		);

		User savedUser = userRepository.save(user);
		return UserResponse.from(savedUser);
	}

	/**
	 * 로그인을 처리하고 Access Token, Refresh Token, 사용자 응답 정보를 반환합니다.
	 * 비밀번호는 평문 비교가 아닌 암호화된 값 비교가 필요하므로 PasswordEncoder로 검증하고,
	 * 응답에는 엔티티를 직접 노출하지 않기 위해 DTO로 변환한 사용자 정보만 반환합니다.
	 */
	@Transactional
	public LoginResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

		validatePassword(request.getPassword(), user.getPassword());

		String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getRole());
		String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

		user.updateRefreshToken(refreshToken, toLocalDateTime(jwtTokenProvider.extractExpiration(refreshToken)));
		User savedUser = userRepository.save(user);

		return LoginResponse.of(accessToken, refreshToken, UserResponse.from(savedUser));
	}

	/**
	 * 동일한 이메일이 이미 존재하는 경우 회원가입을 막습니다.
	 */
	private void validateDuplicatedEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
		}
	}

	/**
	 * 저장된 암호화 비밀번호와 입력 비밀번호를 비교합니다.
	 */
	private void validatePassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
		}
	}

	/**
	 * 토큰 만료 시각을 엔티티 저장에 사용할 LocalDateTime으로 변환합니다.
	 */
	private LocalDateTime toLocalDateTime(java.util.Date expiration) {
		return LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault());
	}
}
