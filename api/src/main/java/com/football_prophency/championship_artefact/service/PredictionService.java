package com.football_prophency.championship_artefact.service;

import com.football_prophency.championship_artefact.enums.MatchStatus;
import com.football_prophency.championship_artefact.model.Match;
import com.football_prophency.championship_artefact.model.Prediction;
import com.football_prophency.championship_artefact.repository.PredictionRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
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
    Match match = prediction.getMatch();
    if (MatchStatus.FINISHED.equals(match.getStatus())) {
      throw new IllegalStateException("Cannot predict on finished match");
    }

    // Проверяем, нет ли уже прогноза от этого пользователя на этот матч
    final var exists = predictionRepository.existsByMatchAndUser(prediction.getMatch(), prediction.getUser());
    if (exists) {
      throw new IllegalStateException("Prediction already exists for this match");
    }

    prediction.setCreatedAt(ZonedDateTime.now());
    prediction.setUpdatedAt(ZonedDateTime.now());
    prediction.setPoints(0);

    return predictionRepository.save(prediction);
  }

  public Prediction updatePrediction(String id, Integer homeScore, Integer awayScore) {
    Prediction prediction = getPredictionById(id);
    Match match = prediction.getMatch();

    if (MatchStatus.FINISHED.equals(match.getStatus())) {
      throw new IllegalStateException("Cannot update prediction for finished match");
    }

    prediction.setHomeScore(homeScore);
    prediction.setAwayScore(awayScore);
    prediction.setUpdatedAt(ZonedDateTime.now());

    return predictionRepository.save(prediction);
  }

  public void recalculatePointsForMatch(String matchId) {
    Match match = matchService.getMatchById(matchId);
    if (!MatchStatus.FINISHED.equals(match.getStatus())) {
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
