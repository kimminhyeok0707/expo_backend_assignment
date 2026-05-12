package com.expo.backendassignment.global.config;

import com.expo.backendassignment.global.exception.ErrorCode;
import com.expo.backendassignment.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 인증은 되었지만 권한이 부족한 요청에 공통 형식의 403 응답을 내려주는 핸들러입니다.
 */
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException
	) throws IOException, ServletException {
		ErrorCode errorCode = ErrorCode.ACCESS_DENIED;

		response.setStatus(errorCode.getStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
			objectMapper.writeValueAsString(ApiResponse.fail(errorCode.getCode(), errorCode.getMessage()))
		);
	}
}
