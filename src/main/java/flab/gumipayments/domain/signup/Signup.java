package flab.gumipayments.domain.signup;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Signup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signup_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SignupStatus status;

    private String signupKey;

    @Builder
    public Signup(String signupKey) {
        this.signupKey = signupKey;
        status = SignupStatus.SIGNUP_REQUEST;
    }

    public void accept() {
        status = SignupStatus.ACCEPT;
    }

    public void timeout() {
        status = SignupStatus.TIMEOUT;
    }
}
