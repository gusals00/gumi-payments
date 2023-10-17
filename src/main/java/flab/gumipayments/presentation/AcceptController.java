package flab.gumipayments.presentation;

import flab.gumipayments.application.ReAcceptRequesterApplication;
import flab.gumipayments.application.SignupAcceptApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/accept")
public class AcceptController {

    private final SignupAcceptApplication signupAcceptManagerApplication;
    private final ReAcceptRequesterApplication reAcceptRequesterApplication;

    // 인증 수락
    @PostMapping("/signup/{acceptId}")
    public ResponseEntity signupAccept(@PathVariable String acceptId) {
        signupAcceptManagerApplication.accept(acceptId);

        return ResponseEntity.ok("");
    }

    // 재인증 요청
    @PostMapping("/re/{signupRequestId}")
    public ResponseEntity reAcceptRequest(@PathVariable Long signupRequestId) {
        reAcceptRequesterApplication.reAcceptRequest(signupRequestId);

        return ResponseEntity.ok("");
    }
}
