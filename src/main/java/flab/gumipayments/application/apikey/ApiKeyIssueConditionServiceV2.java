package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static flab.gumipayments.domain.contract.ContractStatus.*;

@Service
@RequiredArgsConstructor
public class ApiKeyIssueConditionServiceV2 {

    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyIssueCondition getIssueCondition(ApiKeyType type){
        if(type==ApiKeyType.PRODUCTION){
            return existContract()
                    .and(existApiKey().not());
        }else{
            return existAccount()
                    .and(existApiKey().not());
        }
    }

    public ApiKeyIssueCondition getReIssueCondition(ApiKeyType type){
        if(type==ApiKeyType.PRODUCTION){
            return existAccount()
                    .and(existContract())
                    .and(existApiKey());
        }else{
            return existAccount()
                    .and(existApiKey());
        }
    }


    private ApiKeyIssueCondition existAccount(){
        return candidate -> accountRepository.existsById(candidate.getAccountId());
    }

    private ApiKeyIssueCondition existContract(){
        return candidate -> contractRepository.existsByAccountIdAndStatus(candidate.getAccountId(), CONTRACT_COMPLETE);
    }

    private ApiKeyIssueCondition existApiKey(){
        return candidate -> apiKeyRepository.existsByAccountIdAndType(candidate.getAccountId(), candidate.getApiKeyType());
    }

}
