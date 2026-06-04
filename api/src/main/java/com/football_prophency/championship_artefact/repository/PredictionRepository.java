package com.football_prophency.championship_artefact.repository;

import com.football_prophency.championship_artefact.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, String> {
  List<Prediction> findByMatchId(String matchId);
  List<Prediction> findByUserId(String userId);
  List<Prediction> findByMatchIdAndUserId(String matchId, String userId);
}
