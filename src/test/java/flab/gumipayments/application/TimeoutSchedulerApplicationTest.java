package flab.gumipayments.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.*;

@SpringBootTest(
        properties = "schedules.cron.timeout=0/1 * * * * ?"
)
class TimeoutSchedulerApplicationTest {

    @SpyBean
    private TimeoutSchedulerApplication timeoutSchedulerApplication;

    @Test
    @DisplayName("timeout 스케줄러 동작")
    public void timeout() {
        await()
                .atMost(2, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(timeoutSchedulerApplication, atLeastOnce()).timeout());
    }
}