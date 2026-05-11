package com.expo.backendassignment.global.config;

import com.expo.backendassignment.domain.user.Role;
import com.expo.backendassignment.global.exception.CustomException;
import com.expo.backendassignment.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * 로그인과 토큰 재발급 흐름에서 공통으로 사용하는 JWT를 생성하고 검증합니다.
 */
@Component
public class JwtTokenProvider {

	private static final String ROLE_CLAIM = "role";
	private static final String TOKEN_TYPE_CLAIM = "type";
	private static final String ACCESS_TOKEN_TYPE = "ACCESS";
	private static final String REFRESH_TOKEN_TYPE = "REFRESH";

	private final JwtProperties jwtProperties;
	private final Key signingKey;

	public JwtTokenProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 사용자 식별 정보와 권한을 담은 Access Token을 생성합니다.
	 */
	public String generateAccessToken(Long userId, Role role) {
		return generateToken(String.valueOf(userId), ACCESS_TOKEN_TYPE, role, jwtProperties.getAccessTokenExpiration());
	}

	/**
	 * 토큰 재발급에만 사용하는 Refresh Token을 생성합니다.
	 */
	public String generateRefreshToken(Long userId) {
		return generateToken(String.valueOf(userId), REFRESH_TOKEN_TYPE, null, jwtProperties.getRefreshTokenExpiration());
	}

	/**
	 * 유효한 토큰을 Spring Security Authentication 객체로 변환합니다.
	 */
	public Authentication getAuthentication(String token) {
		String subject = extractSubject(token);
		Role role = extractRole(token);

		if (role == null) {
			return new UsernamePasswordAuthenticationToken(subject, token, Collections.emptyList());
		}

		return new UsernamePasswordAuthenticationToken(
			subject,
			token,
			List.of(new SimpleGrantedAuthority(role.name()))
		);
	}

	public String extractSubject(String token) {
		return parseClaims(token).getSubject();
	}

	public Role extractRole(String token) {
		String role = parseClaims(token).get(ROLE_CLAIM, String.class);
		return role == null ? null : Role.valueOf(role);
	}

	public String extractTokenType(String token) {
		return parseClaims(token).get(TOKEN_TYPE_CLAIM, String.class);
	}

	public Date extractExpiration(String token) {
		return parseClaims(token).getExpiration();
	}

	public boolean isTokenValid(String token) {
		try {
			parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException exception) {
			return false;
		}
	}

	/**
	 * 토큰 형식, 서명, 만료 여부를 검증하고 실패 시 도메인 예외를 발생시킵니다.
	 */
	public void validateToken(String token) {
		try {
			parseSignedClaims(token);
		} catch (ExpiredJwtException exception) {
			throw new CustomException(ErrorCode.EXPIRED_TOKEN);
		} catch (JwtException | IllegalArgumentException exception) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}

	/**
	 * 리프레시 토큰의 형식, 서명, 만료 여부와 토큰 타입을 함께 검증합니다.
	 */
	public void validateRefreshToken(String token) {
		validateToken(token);

		if (!REFRESH_TOKEN_TYPE.equals(extractTokenType(token))) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}

	private String generateToken(String subject, String tokenType, Role role, long expirationMillis) {
		Date issuedAt = new Date();
		Date expiresAt = new Date(issuedAt.getTime() + expirationMillis);

		return Jwts.builder()
			.subject(subject)
			.claim("nonce", UUID.randomUUID().toString())
			.claim(TOKEN_TYPE_CLAIM, tokenType)
			.claim(ROLE_CLAIM, role != null ? role.name() : null)
			.issuedAt(issuedAt)
			.expiration(expiresAt)
			.signWith(signingKey)
			.compact();
	}

	private Claims parseClaims(String token) {
		return parseSignedClaims(token).getPayload();
	}

	private Jws<Claims> parseSignedClaims(String token) {
		return Jwts.parser()
			.verifyWith((javax.crypto.SecretKey) signingKey)
			.build()
			.parseSignedClaims(token);
	}
}
