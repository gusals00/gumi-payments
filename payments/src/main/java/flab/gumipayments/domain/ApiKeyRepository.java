package flab.gumipayments.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    Optional<ApiKey> findBySecretKey(String secretKey);
    Optional<ApiKey> findByClientKey(String clientKey);
    Optional<ApiKey> findBySecretKeyOrClientKey(String key);
}
