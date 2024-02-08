package flab.gumipayments.application;

import flab.gumipayments.domain.*;
import flab.gumipayments.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static flab.gumipayments.domain.ContractStatus.CONTRACT_COMPLETE;

@Service
@RequiredArgsConstructor
public class ApiKeyFactorCreatorApplication {

    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final ApiKeyRepository apiKeyRepository;

    // issueFactor 생성
    public IssueFactor getIssueFactor(ApiKeyIssueCreateCommand issueCreateCommand) {
        ApiKeyType keyType = ApiKeyType.valueOf(issueCreateCommand.getType());
        Long accountId = issueCreateCommand.getAccountId();

        return IssueFactor.builder()
                .accountId(accountId)
                .expireDate(issueCreateCommand.getExpireDate())
                .apiKeyType(keyType)
                .accountExist(accountRepository.existsById(accountId))
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, keyType))
                .contractCompleteExist(contractRepository.existsByAccountIdAndStatus(accountId, CONTRACT_COMPLETE)).build();
    }

    //reIssueFactor 생성
    public ReIssueFactor getReIssueFactor(ApiKeyReIssueCreateCommand reIssueCreateCommand) {
        ApiKeyType keyType = ApiKeyType.valueOf(reIssueCreateCommand.getType());
        Long accountId = reIssueCreateCommand.getAccountId();

        return ReIssueFactor.builder()
                .accountId(accountId)
                .expireDate(reIssueCreateCommand.getExpireDate())
                .apiKeyType(keyType)
                .accountExist(accountRepository.existsById(accountId))
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, keyType))
                .contractCompleteExist(contractRepository.existsByAccountIdAndStatus(accountId, CONTRACT_COMPLETE)).build();
    }
}
