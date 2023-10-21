package flab.gumipayments.domain.signup;

import flab.gumipayments.domain.KeyFactory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupAccept {

    @Id
    @Column(name = "signup_key")
    private String key;

    private LocalDateTime expireDate;

    @Builder
    public SignupAccept(String key, LocalDateTime expireDate) {
        this.expireDate = expireDate;
        this.key = key;
    }
}
