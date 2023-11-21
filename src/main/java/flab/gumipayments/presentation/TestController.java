package flab.gumipayments.presentation;

import flab.gumipayments.apifirst.openapi.test.domain.TestGet200Response;
import flab.gumipayments.apifirst.openapi.test.rest.TestApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
public class TestController implements TestApi {
    @Override
    public ResponseEntity<TestGet200Response> testGet() {
        return TestApi.super.testGet();
    }
}
