package com.football_prophency.championship_artefact.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {
  @Id
  private String id;
  private String matchId;
  private String userId;
  private Integer homeScore;
  private Integer awayScore;
  private Integer points; // 0, 1, 3
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
