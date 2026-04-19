package org.viora.viorastreamingcore.content.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "content_cast",
    uniqueConstraints = @UniqueConstraint(columnNames = {"content_id", "cast_member_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentToCastModel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "content_id", nullable = false)
  private ContentModel content;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "cast_member_id", nullable = false)
  private CastMemberModel castMember;

  @Column(nullable = false)
  private String roleName;
}