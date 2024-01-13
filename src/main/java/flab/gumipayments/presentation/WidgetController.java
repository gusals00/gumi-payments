package flab.gumipayments.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/payment-widget")
@Slf4j
public class WidgetController {

    @GetMapping
    public String getWidget() throws IOException {
        log.info("결제 위젯 js 파일 호출");
        Resource resource = new ClassPathResource("static/gumi.js");
        return resource.getContentAsString(StandardCharsets.UTF_8);
    }
}
