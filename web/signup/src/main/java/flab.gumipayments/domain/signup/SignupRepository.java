package flab.gumipayments.domain.signup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    Optional<Signup> findBySignupKey(String signupKey);

    Optional<Signup> findByEmail(String email);
}
