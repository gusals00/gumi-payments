package flab.gumipayments.domain.apiKey;

import flab.gumipayments.domain.signup.Signup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    boolean existsByAccountIdAndType(Long accountId,ApiKeyType keyType);

    Optional<ApiKey> findByAccountIdAndType(Long accountId,ApiKeyType keyType);
}
