package com.expo.backendassignment.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입, 로그인, Refresh Token 재발급 흐름에서 사용하는 사용자 엔티티입니다.
 */
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 255)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(nullable = false, length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private Role role;

	@Column(name = "refresh_token", length = 500)
	private String refreshToken;

	@Column(name = "refresh_token_expired_at")
	private LocalDateTime refreshTokenExpiredAt;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	/**
	 * 인증 기능에 필요한 최소 사용자 정보를 생성합니다.
	 */
	public User(String email, String password, String name, Role role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	/**
	 * 가장 최근에 발급한 Refresh Token과 만료 시각을 사용자 정보에 저장합니다.
	 */
	public void updateRefreshToken(String refreshToken, LocalDateTime refreshTokenExpiredAt) {
		this.refreshToken = refreshToken;
		this.refreshTokenExpiredAt = refreshTokenExpiredAt;
	}

	/**
	 * 더 이상 신뢰하면 안 되는 Refresh Token 정보를 제거합니다.
	 */
	public void clearRefreshToken() {
		this.refreshToken = null;
		this.refreshTokenExpiredAt = null;
	}

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
