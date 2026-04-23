package org.viora.viorastreamingcore.content.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "actors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(nullable = false)
  private String name;
  private String photo;

}
