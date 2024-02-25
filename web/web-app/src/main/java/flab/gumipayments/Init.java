package flab.gumipayments;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Init {

    private final InitService service;

    @PostConstruct
    public void init() {
        service.init();
    }
}
