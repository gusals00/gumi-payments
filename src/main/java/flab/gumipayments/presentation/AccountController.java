package flab.gumipayments.presentation;

import flab.gumipayments.application.AccountCreateManagerApplication;
import flab.gumipayments.domain.account.AccountCreateCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@Slf4j
public class AccountController {

    private final AccountCreateManagerApplication accountCreateManagerApplication;

    // 계정 생성
    @PostMapping("/{signupId}")
    public ResponseEntity createAccount(@PathVariable Long signupId, @RequestBody @Valid AccountCreateRequest createRequest) {
        accountCreateManagerApplication.create(convert(createRequest), signupId);

        return ResponseEntity.ok().build();
    }

    private AccountCreateCommand convert(AccountCreateRequest createRequest) {
        return new AccountCreateCommand(createRequest.getPassword(), createRequest.getName());
    }

    @NoArgsConstructor
    @Getter
    @Setter
    static class AccountCreateRequest {
        @Pattern(regexp = "[a-zA-Z0-9]{6,12}", message = "비밀번호는 영어와 숫자를 포함한 6~12자리 이내로 입력해주세요.")
        private String password;
        @NotBlank(message = "이름을 입력해주세요")
        @Size(min = 2, max = 16, message = "이름은 2자 이상 16자 이하여야 합니다.")
        private String name;
    }

    @AllArgsConstructor
    @Getter
    static class Message{
        private String message;
    }

}
