package com.expo.backendassignment.domain.user.service;

import com.expo.backendassignment.domain.user.Role;
import com.expo.backendassignment.domain.user.User;
import com.expo.backendassignment.domain.user.dto.SignUpRequest;
import com.expo.backendassignment.domain.user.dto.UserResponse;
import com.expo.backendassignment.domain.user.repository.UserRepository;
import com.expo.backendassignment.global.exception.CustomException;
import com.expo.backendassignment.global.exception.ErrorCode;
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

	/**
	 * 회원가입을 처리하고 응답 전용 DTO를 반환합니다.
	 * 비밀번호는 원문 저장을 막기 위해 반드시 암호화해서 저장하고,
	 * 엔티티는 내부 모델이므로 외부 응답에는 DTO로 변환해서 반환합니다.
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
	 * 동일한 이메일이 이미 존재하는 경우 회원가입을 막습니다.
	 */
	private void validateDuplicatedEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
		}
	}
}
