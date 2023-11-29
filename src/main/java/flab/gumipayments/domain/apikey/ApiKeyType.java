package flab.gumipayments.domain.apikey;

public enum ApiKeyType {
    TEST("테스트"),
    PROD("실서비스");

    private final String message;

    ApiKeyType(String message) {
        this.message = message;
    }
}
