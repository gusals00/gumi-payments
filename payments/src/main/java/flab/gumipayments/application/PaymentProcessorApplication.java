package flab.gumipayments.application;

import flab.gumipayments.domain.ConfirmCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProcessorApplication {

    private final PaymentConfirmApplication confirmApplication;

    public void confirm(ConfirmCommand confirmCommand) {
        confirmApplication.confirm(confirmCommand);
        confirmApplication.sendConfirmRequestToPaymentAuthority(confirmCommand);
    }
}
