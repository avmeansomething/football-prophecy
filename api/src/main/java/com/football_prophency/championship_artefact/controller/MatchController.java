package com.football_prophency.championship_artefact.controller;

import com.football_prophency.championship_artefact.model.Match;
import com.football_prophency.championship_artefact.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
@Tag(name = "Matches", description = "Управление матчами")
@CrossOrigin(origins = "http://localhost:4200")
public class MatchController {

  private final MatchService matchService;

  @GetMapping
  @Operation(summary = "Получить все матчи")
  public List<Match> getAllMatches() {
    return matchService.getAllMatches();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Получить матч по ID")
  public Match getMatchById(@PathVariable String id) {
    return matchService.getMatchById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Создать матч (только админ)")
  public Match createMatch(@Valid @RequestBody Match match) {
    return matchService.createMatch(match);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Обновить матч (только админ)")
  public Match updateMatch(@PathVariable String id, @Valid @RequestBody Match match) {
    return matchService.updateMatch(id, match);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Удалить матч (только админ)")
  public void deleteMatch(@PathVariable String id) {
    matchService.deleteMatch(id);
  }

  @PostMapping("/{id}/score")
  @Operation(summary = "Установить счёт матча (только админ)")
  public Match setMatchScore(@PathVariable String id,
                             @RequestParam Integer homeScore,
                             @RequestParam Integer awayScore) {
    return matchService.setMatchScore(id, homeScore, awayScore);
  }
}
