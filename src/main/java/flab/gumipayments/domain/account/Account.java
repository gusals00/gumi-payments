package flab.gumipayments.domain.account;

import flab.gumipayments.domain.signup.Signup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signup_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    @OneToOne
    @JoinColumn(name = "signup_id")
    private Signup signup;

    @Builder
    public Account(String email, String password, String name, Signup signup) {
        this.email = email;
        this.password = password;
        this.name = name;
        if (signup != null) {
            this.signup = signup;
        }
    }
}
