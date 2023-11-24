package flab.gumipayments.application.apikey;

import flab.gumipayments.application.apikey.condition.specification.CompositeApiKeyIssueCondition;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.domain.apikey.ApiKeyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiKeyIssueRequesterApplicationTest {

    @InjectMocks
    ApiKeyIssueRequesterApplication sut;

    @Mock
    ApiKeyRepository apiKeyRepository;
    @Mock
    ApiKeyCreatorApplication apiKeyCreatorApplication;


    @Test
    @DisplayName("성공 : API 키 발급을 성공한다.")
    void issueApiKey() {
        when(apiKeyCreatorApplication.create(any())).thenReturn(ApiKeyResponse.builder().build());
        sut.setApiKeyIssueCondition(new CompositeApiKeyIssueCondition() {
            @Override
            public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
                return true;
            }
        });
        sut.issueApiKey(ApiKeyIssueCommand.builder().build());

        verify(apiKeyRepository).save(any());
    }
}