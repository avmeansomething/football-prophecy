package com.football_prophency.championship_artefact.model;

import com.football_prophency.championship_artefact.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "matches", indexes = {
  @Index(name = "idx_match_status", columnList = "status"),
  @Index(name = "idx_match_stage", columnList = "stage"),
  @Index(name = "idx_match_datetime", columnList = "date_time")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "home_team_id", nullable = false)
  private Team homeTeam;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "away_team_id", nullable = false)
  private Team awayTeam;

  @Column(name = "date_time", nullable = false)
  private ZonedDateTime dateTime;

  @Column(nullable = false)
  private String stage;

  @Column(nullable = false)
  private String groupName;

  @Column(name = "home_score")
  private Integer homeScore;

  @Column(name = "away_score")
  private Integer awayScore;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MatchStatus status = MatchStatus.SCHEDULED;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;
}
