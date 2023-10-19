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
public class SignupAccept {

    @Id
    @Column(name = "signup_key")
    private String key;

    private LocalDateTime expire_date;

    @Builder
    public SignupAccept(String key, LocalDateTime expire_date) {
        this.key = key;
        this.expire_date = expire_date;
    }
}
