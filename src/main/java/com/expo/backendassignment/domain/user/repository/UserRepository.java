package com.expo.backendassignment.domain.user.repository;

import com.expo.backendassignment.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사용자 저장과 인증 관련 조회를 담당하는 Repository입니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	Optional<User> findByRefreshToken(String refreshToken);
}
