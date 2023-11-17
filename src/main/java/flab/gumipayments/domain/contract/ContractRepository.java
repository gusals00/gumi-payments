package flab.gumipayments.domain.contract;


import org.springframework.data.jpa.repository.JpaRepository;


public interface ContractRepository extends JpaRepository<Contract, Long> {
    boolean existsByAccountId(Long accountId);
}
