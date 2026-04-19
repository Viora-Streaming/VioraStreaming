package org.viora.viorastreamingcore.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CastMember {
  private Long Id;
  private String name;
  private String roleName;
}
