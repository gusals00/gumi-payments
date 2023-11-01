package flab.gumipayments.domain.signup;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static flab.gumipayments.domain.signup.SignupStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Signup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signup_id")
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private SignupStatus status;

    @Column(unique = true)
    private String signupKey;

    private LocalDateTime expireDate;

    @Builder
    public Signup(String email, String signupKey, LocalDateTime expireDate) {
        this.email = email;
        this.signupKey = signupKey;
        this.expireDate = expireDate;
        status = SIGNUP_REQUEST;
    }

    public void accept() {
        if (LocalDateTime.now().isAfter(expireDate)) {
            throw new IllegalStateException("만료 시간이 지났습니다.");
        }
        updateStatus(SIGNUP_REQUEST, ACCEPT);
    }

    public void accountCreated() {
        updateStatus(ACCEPT, ACCOUNT_CREATED);
    }

    private void updateStatus(SignupStatus fromStatus, SignupStatus toStatus) {
        if (this.status == fromStatus) {
            this.status = toStatus;
        } else {
            throw new IllegalStateException("올바르지 않은 가입 요청 status 변경입니다.");
        }
    }
}
