package flab.gumipayments.presentation;

import flab.gumipayments.apifirst.openapi.account.domain.AccountCreateRequest;
import flab.gumipayments.apifirst.openapi.account.rest.AccountApi;
import flab.gumipayments.application.account.AccountCreateManagerApplication;
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
public class AccountController implements AccountApi {

    private final AccountCreateManagerApplication accountCreateManagerApplication;


    // 계정 생성
    @PostMapping("/{signupId}")
    @Override
    public ResponseEntity<Void> createAccount(Long signupId, AccountCreateRequest createRequest) {
        accountCreateManagerApplication.create(convert(createRequest), signupId);
        return ResponseEntity.ok().build();
    }

    private AccountCreateCommand convert(AccountCreateRequest createRequest) {
        return new AccountCreateCommand(createRequest.getPassword(), createRequest.getName());
    }
}
