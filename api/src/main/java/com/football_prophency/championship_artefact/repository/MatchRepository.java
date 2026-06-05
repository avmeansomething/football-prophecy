package com.football_prophency.championship_artefact.repository;

import com.football_prophency.championship_artefact.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {
  List<Match> findByStatus(String status);
  List<Match> findByStage(String stage);
  List<Match> findByDateTimeBetween(ZonedDateTime start, ZonedDateTime end);
  List<Match> findByGroupName(String groupName);
}
