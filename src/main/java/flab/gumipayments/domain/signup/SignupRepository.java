package flab.gumipayments.domain.signup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    Optional<Signup> findBySignupKey(String signupKey);

    @Modifying(clearAutomatically = true)
    @Query("update Signup s set s.status = flab.gumipayments.domain.signup.SignupStatus.TIMEOUT where s.expireDate < :datetime and s.status = :status")
    int updateAllTimeoutSignup(@Param("datetime") LocalDateTime dateTime, @Param("status") SignupStatus status);

    Optional<Signup> findByEmail(String email);
}
