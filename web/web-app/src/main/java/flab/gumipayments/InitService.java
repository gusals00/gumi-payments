package flab.gumipayments;

import flab.gumipayments.application.ApiKeyIssueRequesterApplication;
import flab.gumipayments.application.ApiKeyReIssueRequesterApplication;
import flab.gumipayments.domain.ApiKeyPair;
import flab.gumipayments.domain.ApiKeyType;
import flab.gumipayments.domain.IssueFactor;
import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.infrastructure.KeyEncrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitService {
    private final ApiKeyIssueRequesterApplication issueRequesterApplication;
    private final ApiKeyReIssueRequesterApplication reIssueRequesterApplication;
    private final KeyEncrypt keyEncrypt;

    public void init() {
        ApiKeyPair apiKeyPair = issueRequesterApplication.issueApiKey(IssueFactor.builder().
                apiKeyExist(false).
                apiKeyType(ApiKeyType.TEST).
                accountExist(true).
                accountId(null).
                contractCompleteExist(false).
                expireDate(LocalDateTime.now().plusDays(1))
                .build());

        log.info("===== issue apiKeyPair : secret = {}, client = {} encryptedSecret = {}, encryptedClient = {} ====", apiKeyPair.getSecretKey(), apiKeyPair.getClientKey(),
                keyEncrypt.encrypt(apiKeyPair.getSecretKey()), keyEncrypt.encrypt(apiKeyPair.getClientKey()));

        ApiKeyPair reIssueapiKeyPair = reIssueRequesterApplication.reIssueApiKey(ReIssueFactor.builder()
                .accountId(null)
                .expireDate(LocalDateTime.now().plusDays(1))
                .apiKeyType(ApiKeyType.TEST)
                .accountExist(true)
                .apiKeyExist(true)
                .contractCompleteExist(false)
                .build());

        log.info("===== reissue apiKeyPair : secret = {}, client = {} encryptedSecret = {}, encryptedClient = {} ====", reIssueapiKeyPair.getSecretKey(), reIssueapiKeyPair.getClientKey(),
                keyEncrypt.encrypt(reIssueapiKeyPair.getSecretKey()), keyEncrypt.encrypt(reIssueapiKeyPair.getClientKey()));
    }
}