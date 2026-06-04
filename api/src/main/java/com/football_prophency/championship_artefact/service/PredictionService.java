package com.football_prophency.championship_artefact.service;

import com.football_prophency.championship_artefact.model.Match;
import com.football_prophency.championship_artefact.model.Prediction;
import com.football_prophency.championship_artefact.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionService {

  private final PredictionRepository predictionRepository;
  private final MatchService matchService;
  private final PointsCalculatorService pointsCalculatorService;

  public List<Prediction> getAllPredictions() {
    return predictionRepository.findAll();
  }

  public List<Prediction> getPredictionsByMatch(String matchId) {
    return predictionRepository.findByMatchId(matchId);
  }

  public List<Prediction> getPredictionsByUser(String userId) {
    return predictionRepository.findByUserId(userId);
  }

  public Prediction createPrediction(Prediction prediction) {
    // Проверяем, что матч существует и ещё не закончен
    Match match = matchService.getMatchById(prediction.getMatchId());
    if ("finished".equals(match.getStatus())) {
      throw new IllegalStateException("Cannot predict on finished match");
    }

    // Проверяем, нет ли уже прогноза от этого пользователя на этот матч
    List<Prediction> existing = predictionRepository
      .findByMatchIdAndUserId(prediction.getMatchId(), prediction.getUserId());

    if (!existing.isEmpty()) {
      throw new IllegalStateException("Prediction already exists for this match");
    }

    prediction.setCreatedAt(LocalDateTime.now());
    prediction.setUpdatedAt(LocalDateTime.now());
    prediction.setPoints(0); // временно, будет пересчитано после матча

    return predictionRepository.save(prediction);
  }

  public Prediction updatePrediction(String id, Integer homeScore, Integer awayScore) {
    Prediction prediction = getPredictionById(id);
    Match match = matchService.getMatchById(prediction.getMatchId());

    if ("finished".equals(match.getStatus())) {
      throw new IllegalStateException("Cannot update prediction for finished match");
    }

    prediction.setHomeScore(homeScore);
    prediction.setAwayScore(awayScore);
    prediction.setUpdatedAt(LocalDateTime.now());

    return predictionRepository.save(prediction);
  }

  public void recalculatePointsForMatch(String matchId) {
    Match match = matchService.getMatchById(matchId);
    if (!"finished".equals(match.getStatus())) {
      throw new IllegalStateException("Match is not finished yet");
    }

    List<Prediction> predictions = predictionRepository.findByMatchId(matchId);

    for (Prediction prediction : predictions) {
      int points = pointsCalculatorService.calculatePoints(prediction, match);
      prediction.setPoints(points);
      predictionRepository.save(prediction);
    }

    // TODO: Пересчитать totalPoints у пользователей
    // updateUserTotalPoints();
  }

  private Prediction getPredictionById(String id) {
    return predictionRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Prediction not found with id: " + id));
  }
}
