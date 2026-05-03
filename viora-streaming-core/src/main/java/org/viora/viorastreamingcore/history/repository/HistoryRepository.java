package org.viora.viorastreamingcore.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.viora.viorastreamingcore.content.model.Movie;
import org.viora.viorastreamingcore.history.model.History;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

  Optional<History> findByAccountIdAndMovieId(Long accountId, Long movieId);

  List<History> getHistoryByAccountId(Long accountId);
}
