package flab.gumipayments.infrastructure.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender implements Sender {

    @Override
    public void sendSignupRequest(String toAddress, String acceptKey) {
        System.out.println("Sender.sendSignupRequest");
    }
}
