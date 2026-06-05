package com.football_prophency.championship_artefact.service;

import com.football_prophency.championship_artefact.enums.MatchStatus;
import com.football_prophency.championship_artefact.model.Match;
import com.football_prophency.championship_artefact.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;
  private final PointsCalculatorService pointsCalculatorService;

  public List<Match> getAllMatches() {
    return matchRepository.findAll();
  }

  public Match getMatchById(String id) {
    return matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
  }

  public Match createMatch(Match match) {
    match.setCreatedAt(ZonedDateTime.now());
    match.setUpdatedAt(ZonedDateTime.now());
    match.setStatus(MatchStatus.SCHEDULED);
    return matchRepository.save(match);
  }

  public Match updateMatch(String id, Match matchDetails) {
    Match match = getMatchById(id);
    match.setHomeTeam(matchDetails.getHomeTeam());
    match.setAwayTeam(matchDetails.getAwayTeam());
    match.setDateTime(matchDetails.getDateTime());
    match.setStage(matchDetails.getStage());
    match.setGroupName(matchDetails.getGroupName());
    match.setUpdatedAt(ZonedDateTime.now());
    if (ZonedDateTime.now().isAfter(match.getDateTime())) {
      match.setStatus(MatchStatus.FINISHED);
    }
    return matchRepository.save(match);
  }

  public Match setMatchScore(String id, Integer homeScore, Integer awayScore) {
    Match match = getMatchById(id);
    match.setHomeScore(homeScore);
    match.setAwayScore(awayScore);
    match.setStatus(MatchStatus.FINISHED);
    match.setUpdatedAt(ZonedDateTime.now());

    // Сохраняем результат
    Match savedMatch = matchRepository.save(match);

    return savedMatch;
  }

  public void deleteMatch(String id) {
    matchRepository.deleteById(id);
  }

  public List<Match> getMatchesByStage(String stage) {
    return matchRepository.findByStage(stage);
  }
}
