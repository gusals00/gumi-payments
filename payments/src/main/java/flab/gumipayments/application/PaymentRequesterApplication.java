package flab.gumipayments.application;


import flab.gumipayments.domain.AuthenticateKeyRepository;
import flab.gumipayments.domain.Payment;
import flab.gumipayments.domain.PaymentFactory;
import flab.gumipayments.domain.RequestCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentRequesterApplication {

    private final PaymentFactory paymentFactory;
    private final PaymentAuthenticator paymentAuthenticator;
    private final AuthenticateKeyRepository authenticateKeyRepository;

    // 일단은 카드만 처리
    public String requestPayment(RequestCommand command) {
        // 결제 생성
        Payment payment = paymentFactory.create(command);
        // paymentKey, randomKey 매핑
        String randomKey = UUID.randomUUID().toString();
        authenticateKeyRepository.save(randomKey ,payment.getPaymentKey());

        // 결제 수단 인증 요청
        String callbackUrl = "/api/payments/accept?key="+randomKey + "&paymentKey="+payment.getPaymentKey()+"&isSuccess=";
        return paymentAuthenticator.authenticateUrl(callbackUrl);
    }
}
