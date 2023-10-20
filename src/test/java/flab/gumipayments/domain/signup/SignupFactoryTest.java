package flab.gumipayments.domain.signup;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SignupFactoryTest {

    @Test
    void create() {
        SignupCommand signupCommand = new SignupCommand("love@naver.com","1234","name");
        SignupFactory signupFactory = new SignupFactory();

        Signup signup = signupFactory.create(signupCommand);

        assertThat(signup.getPassword()).isEqualTo(signupCommand.getPassword());
        assertThat(signup.getEmail()).isEqualTo(signupCommand.getEmail());
        assertThat(signup.getName()).isEqualTo(signupCommand.getName());
    }

}