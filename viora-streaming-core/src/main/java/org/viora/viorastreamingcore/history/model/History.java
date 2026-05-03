package org.viora.viorastreamingcore.history.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.content.model.Movie;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "account_id", nullable = false, updatable = false)
  private AccountModel account;

  @ManyToOne
  @JoinColumn(name = "movie_id", nullable = false, updatable = false)
  private Movie movie;

  @Column(nullable = false)
  private Long lastWatchedAt;

}
