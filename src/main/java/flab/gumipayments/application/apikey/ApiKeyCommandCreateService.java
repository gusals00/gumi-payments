package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.*;
import flab.gumipayments.domain.apikey.ApiKeyCreateCommand;
import flab.gumipayments.domain.contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static flab.gumipayments.domain.contract.ContractStatus.*;

@Service
@RequiredArgsConstructor
public class ApiKeyCommandCreateService {

    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final ApiKeyRepository apiKeyRepository;

    // issueCommand 생성
    public ApiKeyIssueCommand getIssueCommand(ApiKeyIssueCreateCommand issueCreateCommand) {
        ApiKeyType keyType = ApiKeyType.valueOf(issueCreateCommand.getType());
        Long accountId = issueCreateCommand.getAccountId();

        return ApiKeyIssueCommand.builder()
                .accountId(accountId)
                .expireDate(issueCreateCommand.getExpireDate())
                .apiKeyType(keyType)
                .accountExist(accountRepository.existsById(accountId))
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, keyType))
                .contractCompleteExist(contractRepository.existsByAccountIdAndStatus(accountId, CONTRACT_COMPLETE)).build();
    }

    public ApiKeyReIssueCommand getReIssueCommand(ApiKeyReIssueCreateCommand reIssueCreateCommand) {
        ApiKeyType keyType = ApiKeyType.valueOf(reIssueCreateCommand.getType());
        Long accountId = reIssueCreateCommand.getAccountId();

        return ApiKeyReIssueCommand.builder()
                .accountId(accountId)
                .expireDate(reIssueCreateCommand.getExpireDate())
                .apiKeyType(keyType)
                .accountExist(accountRepository.existsById(accountId))
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, keyType))
                .contractCompleteExist(contractRepository.existsByAccountIdAndStatus(accountId, CONTRACT_COMPLETE)).build();
    }
}
