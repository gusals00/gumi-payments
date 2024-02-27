package flab.gumipayments.domain;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    READY, // 초기 상태
    EXPIRED, // 결제 유효 시간 만료
    IN_PROGRESS, // 결제 수단 인증 완료
    DONE, // 결제 승인
    ABORTED, // 결제 승인 실패, 결제 수단 인증 실패
    CANCELED, // 승인된 결제가 취소
    PARTIAL_CANCEL; // 승인된 결제가 부분 취소

}
