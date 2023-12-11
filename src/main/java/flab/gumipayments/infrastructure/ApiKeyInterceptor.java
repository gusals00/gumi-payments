package flab.gumipayments.infrastructure;

import flab.gumipayments.domain.apikey.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;

import static org.springframework.http.HttpHeaders.*;

@RequiredArgsConstructor
@Slf4j
public class ApiKeyInterceptor implements HandlerInterceptor {

    private static final String BASIC_PREFIX = "Basic";
    private static final String DELIMITER = ":";
    private final ApiKeyRepository apiKeyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String decodedApiKey = decodeApiKey(request);

        if(isValidKey(decodedApiKey)) {
            return true;
        } else {
            throw new ApiKeyNotFoundException("올바르지 않은 ApiKey 입니다.");
        }
    }

    private String decodeApiKey(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        log.info("키 = {}", authorization);
        String apikey = resolveAuthorization(authorization);
        log.info("resolve 후 키 = {}", apikey);
        String decodedApiKey = new String(Base64.getDecoder().decode(apikey)).split(DELIMITER)[0];
        log.info("decode 후 키 = {}", decodedApiKey);
        return decodedApiKey;
    }

    private boolean isValidKey(String decodedApiKey) {
        return apiKeyRepository.existsBySecretKey(passwordEncoder.encode(decodedApiKey));
    }

    private String resolveAuthorization(String authorization) {
        if(StringUtils.hasText(authorization) && authorization.startsWith(BASIC_PREFIX)) {
            return authorization.substring(BASIC_PREFIX.length()).trim();
        } else {
            throw new ApiKeyNotFoundException("ApiKey 형식이 올바르지 않습니다.");
        }
    }
}
