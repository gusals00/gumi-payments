package flab.gumipayments.domain;

public enum ContractStatus {
    CONTRACT_REQUEST("계약 요청"),
    CONTRACT_COMPLETE("계약 완료");

    private final String message;

    ContractStatus(String message) {
        this.message = message;
    }

}
