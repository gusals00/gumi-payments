package flab.gumipayments.application.apikey;

import flab.gumipayments.application.NotFoundException;
import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.ApiKey;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.infrastructure.sender.Sender;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ApiKeyRenewRequesterApplication {

    private final ApiKeyRepository apiKeyRepository;
    private final AccountRepository accountRepository;
    private final Sender sender;

    //api 키 갱신
    @Transactional
    public void renew(ApiKeyRenewCommand renewCommand) {

        //api 키 조회
        ApiKey apiKey = apiKeyRepository.findById(renewCommand.getApiKeyId())
                .orElseThrow(() -> new NotFoundException(SystemErrorCode.NOT_FOUND, "api키가 존재하지 않습니다."));

        // 만료 기간 연장
        apiKey.extendExpireDate(renewCommand);

        // 계정 조회
        Account account = accountRepository.findById(apiKey.getAccountId())
                .orElseThrow(() -> new NotFoundException(SystemErrorCode.NOT_FOUND, "account가 존재하지 않습니다."));

        // 만료 기간 연장 메일 전송
        sender.sendApiKeyRenewRequest(account.getEmail(), renewCommand.getExtendDate());
    }
}
