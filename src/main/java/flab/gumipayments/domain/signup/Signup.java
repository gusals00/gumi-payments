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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signup_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SignupStatus status;

    private String email;

    private String password;

    private String name;

    @OneToOne
    @JoinColumn(name = "signup_key")
    private SignupAccept signupAccept;

    @Builder
    public Signup(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        status = SignupStatus.SIGNUP_REQUEST;
    }

    public void accept() {
        status = SignupStatus.ACCEPT;
    }

    public void timeout() {
        status = SignupStatus.TIMEOUT;
    }
}
