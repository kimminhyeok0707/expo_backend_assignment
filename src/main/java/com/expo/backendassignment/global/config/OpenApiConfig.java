package com.expo.backendassignment.global.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI에서 JWT Bearer 인증 버튼을 노출하기 위한 OpenAPI 설정입니다.
 */
@Configuration
@SecurityScheme(
	name = "JWT",
	type = SecuritySchemeType.HTTP,
	scheme = "bearer",
	bearerFormat = "JWT",
	in = SecuritySchemeIn.HEADER,
	description = "로그인으로 발급받은 Access Token 값을 입력합니다."
)
public class OpenApiConfig {
}
