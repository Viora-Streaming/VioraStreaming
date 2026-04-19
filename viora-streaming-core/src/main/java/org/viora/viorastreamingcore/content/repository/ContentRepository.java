package org.viora.viorastreamingcore.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.viora.viorastreamingcore.content.model.ContentModel;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentModel, Long> {
  Optional<ContentModel> getByTitle(String title);

  boolean existsByTitle(String title);
}
