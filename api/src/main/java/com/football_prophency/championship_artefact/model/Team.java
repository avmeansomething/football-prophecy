package com.football_prophency.championship_artefact.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "teams", indexes = {
  @Index(name = "idx_team_group", columnList = "team_group")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(name = "team_group", nullable = false, length = 1)
  private String group;

  private String flag;
  private Integer fifaRanking;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;
}
