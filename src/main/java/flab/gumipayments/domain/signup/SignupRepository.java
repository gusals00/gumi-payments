package flab.gumipayments.domain.signup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    @Query("select s from Signup s where s.signupKey = :signupKey")
    Optional<Signup> findByAcceptKey(String signupKey);
    @Query("select s from Signup s where s.status = flab.gumipayments.domain.signup.SignupStatus.TIMEOUT")
    List<Signup> findAllTimeoutSignup();
}
