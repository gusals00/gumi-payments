package flab.gumipayments.infrastructure.sender;

public interface Sender {
    void sendSignupRequest(String toAddress, String acceptKey);
}
