package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import static flab.gumipayments.domain.contract.ContractStatus.*;

@Service
@RequiredArgsConstructor
public class ApiKeyIssueConditionServiceV1 {

    private final ApiKeyIssueCondition productIssueCondition;
    private final ApiKeyIssueCondition testIssueCondition;

    private final ApiKeyIssueCondition productReIssueCondition;
    private final ApiKeyIssueCondition testReIssueCondition;


    public ApiKeyIssueCondition getIssueCondition(ApiKeyType type){
        if(type==ApiKeyType.PRODUCTION){
            return productIssueCondition;
        }else{
            return testIssueCondition;
        }
    }

    public ApiKeyIssueCondition getReIssueCondition(ApiKeyType type){
        if(type==ApiKeyType.PRODUCTION){
            return productReIssueCondition;
        }else{
            return testReIssueCondition;
        }
    }

    @Configuration
    @RequiredArgsConstructor
    static class ApiKeyIssueConditionConfig{

        private final AccountRepository accountRepository;
        private final ContractRepository contractRepository;
        private final ApiKeyRepository apiKeyRepository;

        // production api 키 발급 조건
        @Bean
        public ApiKeyIssueCondition productIssueCondition(){
            return existAccount()
                    .and(existContract())
                    .and(existApiKey().not());
        }

        // production api 키 재발급 조건
        @Bean
        public ApiKeyIssueCondition productReIssueCondition(){
            return existAccount()
                    .and(existContract())
                    .and(existApiKey());
        }

        // test api 키 발급 조건
        @Bean
        public ApiKeyIssueCondition testIssueCondition(){
            return existAccount()
                    .and(existApiKey().not());
        }

        // test api 키 재발급 조건
        @Bean
        public ApiKeyIssueCondition testReIssueCondition(){
            return existAccount()
                    .and(existApiKey());
        }

        @Bean
        public ApiKeyIssueCondition existAccount(){
            return candidate -> accountRepository.existsById(candidate.getAccountId());
        }

        @Bean
        public ApiKeyIssueCondition existContract(){
            return candidate -> contractRepository.existsByAccountIdAndStatus(candidate.getAccountId(), CONTRACT_COMPLETE);
        }

        @Bean
        public ApiKeyIssueCondition existApiKey(){
            return candidate -> apiKeyRepository.existsByAccountIdAndType(candidate.getAccountId(), candidate.getApiKeyType());
        }

    }
}
