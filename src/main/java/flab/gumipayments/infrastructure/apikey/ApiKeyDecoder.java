package flab.gumipayments.infrastructure.apikey;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class ApiKeyDecoder {

    private static final String BASIC_PREFIX = "Basic";
    private static final String DELIMITER = ":";

    public String decodeApiKey(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        log.info("키 = {}", authorization);
        String apikey = resolveApiKey(authorization);
        log.info("resolve 후 키 = {}", apikey);
        String decodedApiKey = new String(Base64.getDecoder().decode(apikey)).split(DELIMITER)[0];
        log.info("decode 후 키 = {}", decodedApiKey);
        return decodedApiKey;
    }

    private String resolveApiKey(String authorization) {
        if(StringUtils.hasText(authorization) && authorization.startsWith(BASIC_PREFIX)) {
            return authorization.substring(BASIC_PREFIX.length()).trim();
        } else {
            throw new ApiKeyFormatException("ApiKey 형식이 올바르지 않습니다.");
        }
    }
}
