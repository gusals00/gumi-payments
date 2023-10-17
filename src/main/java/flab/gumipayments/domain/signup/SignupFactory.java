package flab.gumipayments.domain.signup;

public class SignupFactory {

    public Signup create(SignupCommand signupCommand) {
        return new Signup();
    }
}
