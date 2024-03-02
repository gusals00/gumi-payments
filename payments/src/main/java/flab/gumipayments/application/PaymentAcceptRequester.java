package flab.gumipayments.application;

import flab.gumipayments.domain.AuthenticateKeyRepository;
import flab.gumipayments.domain.Payment;
import flab.gumipayments.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentAcceptRequester {

    private final AuthenticateKeyRepository authenticateKeyRepository;
    private final PaymentRepository paymentRepository;

    public String authenticate(String paymentKey, String randomKey, boolean isSuccess) {
        Payment payment = paymentRepository.findById(paymentKey)
                .orElseThrow(() -> new NotFoundSystemException("payment가 존재하지 않습니다."));

        if (!isSuccess)
            throw new AuthenticatorAcceptException("인증사에서 인증이 실패했습니다.", payment.getOrderId(), payment.getFailUrl());

        // 올바른 random Key가 아닌 경우
        if (!authenticateKeyRepository.isValid(randomKey, paymentKey)) {
            throw new PaymentAcceptException("올바른 randomKey가 아닙니다.", payment.getOrderId(), payment.getFailUrl());
        }

        payment.updateInProgress();
        return payment.getSuccessUrl() + "?orderId" + payment.getOrderId() + "&" + paymentKey + "&" + payment.getTotalAmount();
    }
}
