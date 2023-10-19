package flab.gumipayments.domain.signup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    Signup findBySignupKey(String signupKey);
    List<Signup> findAllTimeoutSignup();
}
