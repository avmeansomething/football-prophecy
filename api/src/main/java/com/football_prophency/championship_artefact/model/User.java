package com.football_prophency.championship_artefact.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  private String id;

  private String username;

  private String password; // зашифрованный
  private String role; // admin, player
  private Integer totalPoints = 0;

  private List<Prediction> predictions = new ArrayList<>();
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
