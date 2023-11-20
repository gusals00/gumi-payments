package flab.gumipayments.domain.contract;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ContractRepository extends JpaRepository<Contract, Long> {
    boolean existsByAccountIdAndStatus(Long accountId,ContractStatus status);
    Optional<Contract> findByAccountId(Long accountId);
}
