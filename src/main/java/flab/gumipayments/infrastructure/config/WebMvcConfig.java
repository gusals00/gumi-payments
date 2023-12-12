package flab.gumipayments.infrastructure.config;

import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.infrastructure.apikey.ApiKeyArgumentResolver;
import flab.gumipayments.infrastructure.apikey.ApiKeyDecoder;
import flab.gumipayments.infrastructure.apikey.ApiKeyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiKeyRepository apiKeyRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApiKeyDecoder apiKeyDecoder;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiKeyInterceptor(apiKeyRepository, passwordEncoder, apiKeyDecoder))
                .addPathPatterns("/api/payments/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ApiKeyArgumentResolver(apiKeyRepository, passwordEncoder, apiKeyDecoder));
    }
}