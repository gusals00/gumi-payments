package flab.gumipayments.infrastructure.sender;

import org.springframework.stereotype.Component;

@Component
public class EmailSender implements Sender {
    @Override
    public void send() {
        System.out.println("EmailSender.send");
    }
}
