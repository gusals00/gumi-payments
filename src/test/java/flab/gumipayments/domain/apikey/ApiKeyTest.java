package flab.gumipayments.domain.apikey;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;


class ApiKeyTest {
    private ApiKey sut;
    private MockedStatic<LocalDateTime> localDateMockedStatic;
    private ApiKey.ApiKeyBuilder sutBuilder;

    private
    @BeforeEach
    void setup() {
        sutBuilder = ApiKey.builder();
        freezeLocalDateTimeNow();
    }

    @AfterEach
    void close() {
        localDateMockedStatic.close();
    }

    private void freezeLocalDateTimeNow() {
        LocalDateTime date = of(2023, 10, 23, 0, 0);
        localDateMockedStatic = Mockito.mockStatic(LocalDateTime.class);
        localDateMockedStatic.when(LocalDateTime::now).thenReturn(date);
    }

    @Test
    @DisplayName("예외: 만료 기간 갱신시, 갱신 날짜 <= 만료 기간 이면 실패한다.")
    void extendFail01() {
        LocalDateTime expireDate = of(2023, 10, 23, 0, 0);
        sut = sutBuilder
                .expireDate(expireDate).build();

        Assertions.assertThatThrownBy(() -> sut.extendExpireDate(expireDate))
                .isInstanceOf(ApiKeyExpireException.class);
        Assertions.assertThatThrownBy(() -> sut.extendExpireDate(expireDate.minusNanos(1)))
                .isInstanceOf(ApiKeyExpireException.class);
    }


    @Test
    @DisplayName("성공: 만료 기간 갱신시, 갱신 날짜 > 만료 기간 이면 성공한다.")
    void extend() {
        LocalDateTime expireDate = now();
        LocalDateTime renewExpireDate = now().plusNanos(1);

        sut = sutBuilder
                .expireDate(expireDate).build();

        sut.extendExpireDate(renewExpireDate);

        Assertions.assertThat(isEquals(sut.getExpireDate(),renewExpireDate)).isTrue();
    }

    private boolean isEquals(LocalDateTime localDateTime1,LocalDateTime localDateTime2){
        return localDateTime1.compareTo(localDateTime2) == 0;
    }


}