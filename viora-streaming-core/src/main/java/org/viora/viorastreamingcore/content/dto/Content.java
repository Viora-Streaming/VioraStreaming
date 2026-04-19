package org.viora.viorastreamingcore.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Content {
  private Long id;
  private String title;
  private String description;
  private String synopsis;

  private List<CastMember> cast;
}
