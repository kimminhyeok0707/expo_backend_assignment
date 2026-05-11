package com.expo.backendassignment.domain.user;

/**
 * 데이터베이스에 {@link jakarta.persistence.EnumType#STRING} 방식으로 저장되는 사용자 권한 값입니다.
 */
public enum Role {
	user,
	admin
}
