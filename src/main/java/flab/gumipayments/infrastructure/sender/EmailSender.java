package flab.gumipayments.infrastructure.sender;

public class EmailSender implements Sender {
    @Override
    public void send() {
        System.out.println("EmailSender.send");
    }
}
