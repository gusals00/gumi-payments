package flab.gumipayments.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TimeoutSchedulerApplication {

    private final SignupAcceptApplication signupAcceptApplication;

    @Scheduled(cron = "${schedules.cron.timeout}")
    public void timeout() {
        log.info("timeout 스케줄러 실행");
        signupAcceptApplication.timeout();
    }
}
