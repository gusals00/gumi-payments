package flab.gumipayments.domain.signup;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private String signupKey;

    private LocalDateTime expireDate;
    private boolean isAccountCreated;

    @Builder
    public Signup(String email,String signupKey, LocalDateTime expireDate) {
        this.email = email;
        this.signupKey = signupKey;
        this.expireDate = expireDate;
        status = SignupStatus.SIGNUP_REQUEST;
        isAccountCreated = false;
    }

    public void accept() {
        if(status==SignupStatus.TIMEOUT){
            throw new IllegalStateException("올바르지 않은 가입 요청 status 변경입니다.");
        }
        status = SignupStatus.ACCEPT;
    }

    public void timeout() {
        if(status==SignupStatus.ACCEPT){
            throw new IllegalStateException("올바르지 않은 가입 요청 status 변경입니다.");
        }
        status = SignupStatus.TIMEOUT;
    }

    public void accountCreated() {
        isAccountCreated = true;
    }
}
