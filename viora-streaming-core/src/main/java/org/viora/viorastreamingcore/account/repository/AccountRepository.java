package org.viora.viorastreamingcore.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.viora.viorastreamingcore.account.model.AccountModel;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {

  boolean existsByEmail(String login);

  Optional<AccountModel> findByEmail(String login);
}
