package flab.gumipayments.application;

import flab.gumipayments.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentConfirmApplication {

    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void confirm(ConfirmCommand confirmCommand) {
        // 멱등키 검사

        // 승인 조건 검사

        Payment payment = paymentRepository.findById(confirmCommand.getPaymentKey())
                .orElseThrow(() -> new NotFoundSystemException("결제가 존재하지 않습니다."));
        payment.updateDone(PaymentStatus.DONE);

        Transaction transaction = payment.createTransactionByConfirm(confirmCommand);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void sendConfirmRequestToPaymentAuthority(ConfirmCommand confirmCommand) {
        // 결제 수단 인증사에게 결제 승인 요청

        // 실패시 결제 상태 Aborted로 변경
    }
}
