package com.football_prophency.championship_artefact.service;

import com.football_prophency.championship_artefact.model.Match;
import com.football_prophency.championship_artefact.model.Prediction;
import org.springframework.stereotype.Service;

@Service
public class PointsCalculatorService {

  // Java 25 record для результата
  public record CalculationResult(int points, boolean exactScore, boolean winnerCorrect) {}

  public CalculationResult calculatePointsWithDetails(Prediction prediction, Match match) {
    if (match.getHomeScore() == null || match.getAwayScore() == null) {
      return new CalculationResult(0, false, false);
    }

    var exactScore = prediction.getHomeScore().equals(match.getHomeScore()) &&
      prediction.getAwayScore().equals(match.getAwayScore());

    if (exactScore) {
      return new CalculationResult(3, true, true);
    }

    var winnerCorrect = isWinnerCorrect(prediction, match);
    return new CalculationResult(winnerCorrect ? 1 : 0, false, winnerCorrect);
  }

  public int calculatePoints(Prediction prediction, Match match) {
    return calculatePointsWithDetails(prediction, match).points();
  }

  private boolean isWinnerCorrect(Prediction prediction, Match match) {
    var predictedDiff = Integer.compare(prediction.getHomeScore(), prediction.getAwayScore());
    var actualDiff = Integer.compare(match.getHomeScore(), match.getAwayScore());
    return predictedDiff == actualDiff;
  }
}
