package flab.gumipayments.application;

import flab.gumipayments.domain.ApiKey;
import flab.gumipayments.domain.ApiKeyRepository;
import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.infrastructure.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new NotFoundSystemException("api키가 존재하지 않습니다."));

        // 만료 기간 연장
        apiKey.extendExpireDate(renewCommand);

        // 계정 조회
        Account account = accountRepository.findById(apiKey.getAccountId())
                .orElseThrow(() -> new NotFoundSystemException("account가 존재하지 않습니다."));

        // 만료 기간 연장 메일 전송
        sender.sendApiKeyRenewRequest(account.getEmail(), renewCommand.getExtendDate());
    }
}
