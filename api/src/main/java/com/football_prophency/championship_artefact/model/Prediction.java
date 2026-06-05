package com.football_prophency.championship_artefact.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "predictions", indexes = {
  @Index(name = "idx_match_status", columnList = "status"),
  @Index(name = "idx_match_stage", columnList = "stage"),
  @Index(name = "idx_match_datetime", columnList = "date_time")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "match_id", nullable = false)
  private Match match;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "home_score", nullable = false)
  private Integer homeScore;

  @Column(name = "away_score", nullable = false)
  private Integer awayScore;

  @Column(name = "points", nullable = false)
  private Integer points; // 0, 1, 3

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;
}
