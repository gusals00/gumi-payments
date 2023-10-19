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
    @PostMapping("/signup/{signupKey}")
    public ResponseEntity signupAccept(@PathVariable String signupKey) {
        signupAcceptManagerApplication.accept(signupKey);

        return ResponseEntity.ok("");
    }

    // 재인증 요청
    @PostMapping("/re/{signupId}")
    public ResponseEntity reAcceptRequest(@PathVariable Long signupId) {
        reAcceptRequesterApplication.reAcceptRequest(signupId);

        return ResponseEntity.ok("");
    }
}
