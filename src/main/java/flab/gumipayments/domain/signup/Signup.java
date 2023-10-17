package flab.gumipayments.domain.signup;

public class Signup {

    private String status = "가입요청";

    public void accept() {
        status = "인증";
    }

    public void timeout() {
        status = "미인증";
    }
}
