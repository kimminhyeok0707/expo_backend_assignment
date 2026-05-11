package com.expo.backendassignment.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청 본문을 표현하는 DTO입니다.
 */
@Getter
@NoArgsConstructor
public class SignUpRequest {

	@Email(message = "올바른 이메일 형식이어야 합니다.")
	@NotBlank(message = "이메일은 필수입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.")
	private String password;

	@NotBlank(message = "이름은 필수입니다.")
	@Size(max = 100, message = "이름은 100자 이하로 입력해야 합니다.")
	private String name;
}
