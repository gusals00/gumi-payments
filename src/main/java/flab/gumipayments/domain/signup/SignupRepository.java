package flab.gumipayments.domain.signup;

import java.util.List;

public interface SignupRepository {
    Signup save(Signup signup);

    Signup findByAcceptId(String acceptId);
    List<Signup> findTimeoutRequests();

    boolean existsById(Long id);
}
