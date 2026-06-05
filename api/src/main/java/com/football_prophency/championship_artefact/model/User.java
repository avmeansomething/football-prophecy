package com.football_prophency.championship_artefact.model;

import com.football_prophency.championship_artefact.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "users", indexes = {
  @Index(name = "idx_team_group", columnList = "team_group")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password; // зашифрованный

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role; //admin, player

  @Column(name = "total_points", nullable = false)
  private Integer totalPoints = 0;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;
}
