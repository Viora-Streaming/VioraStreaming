package org.viora.viorastreamingcore.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.viora.viorastreamingcore.content.model.CastMemberModel;
import java.util.List;

public interface CastMemberRepository extends JpaRepository<CastMemberModel, Long> {
  List<CastMemberModel> findByName(String name);
  CastMemberModel findById(long id);

  boolean existsAnyByName(String name);
  boolean existsById(long id);
}
