//package flab.gumipayments.infrastructure.apikey;
//
//import flab.gumipayments.domain.apikey.ApiKey;
//import flab.gumipayments.domain.apikey.ApiKeyRepository;
//import flab.gumipayments.infrastructure.encrypt.KeyEncrypt;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//
//import java.time.LocalDateTime;
//
//@RequiredArgsConstructor
//@Slf4j
//public class ApiKeyInterceptor implements HandlerInterceptor {
//
//    private final ApiKeyRepository apiKeyRepository;
//    private final KeyEncrypt keyEncrypt;
//    private final ApiKeyDecoder apiKeyDecoder;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String decodedApiKey = apiKeyDecoder.decodeApiKey(request);
//
//        validateKey(decodedApiKey);
//
//        return true;
//    }
//
//    private void validateKey(String decodedApiKey) {
//        ApiKey apikey = apiKeyRepository.findBySecretKey(keyEncrypt.encrypt(decodedApiKey))
//                .orElseThrow(() -> new ApiKeyNotFoundException("존재하지 않는 ApiKey 입니다."));
//
//        if(apikey.getExpireDate().isBefore(LocalDateTime.now())) { // 만료시간 < 현재시간 인 경우
//            throw new ApiKeyExpiredException("만료된 ApiKey 입니다.");
//        }
//    }
//}
