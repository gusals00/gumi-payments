package flab.gumipayments.application.apiKey;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apiKey.ApiKeyRepository;
import flab.gumipayments.domain.apiKey.ApiKeyType;
import flab.gumipayments.domain.contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiKeyIssueConditionService {

    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyIssueCondition getIssueCondition(ApiKeyType type){
        if(type==ApiKeyType.PRODUCTION){
            return existContractCondition()
                    .and(existApiKeyByAccountIdAndTypeCondition().not());
        }else{
            return existAccountCondition()
                    .and(existApiKeyByAccountIdAndTypeCondition().not());
        }
    }

    public ApiKeyIssueCondition getReIssueCondition(ApiKeyType type){
        if(type==ApiKeyType.PRODUCTION){
            return existAccountCondition()
                    .and(existContractCondition())
                    .and(existApiKeyByAccountIdAndTypeCondition());
        }else{
            return existAccountCondition()
                    .and(existApiKeyByAccountIdAndTypeCondition());
        }
    }

    private ApiKeyIssueCondition existAccountCondition(){
        return candidate -> accountRepository.existsById(candidate.getAccountId());
    }

    private ApiKeyIssueCondition existContractCondition(){
        return candidate -> contractRepository.existsByAccountId(candidate.getAccountId());
    }

    private ApiKeyIssueCondition existApiKeyByAccountIdAndTypeCondition(){
        return candidate -> apiKeyRepository.existsByAccountIdAndType(candidate.getAccountId(), candidate.getApiKeyType());
    }
}
