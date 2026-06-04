package com.football_prophency.championship_artefact.controller;

import com.football_prophency.championship_artefact.model.Prediction;
import com.football_prophency.championship_artefact.service.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
@Tag(name = "Predictions", description = "Управление прогнозами")
@CrossOrigin(origins = "http://localhost:4200")
public class PredictionController {

  private final PredictionService predictionService;

  @GetMapping
  @Operation(summary = "Получить все прогнозы")
  public List<Prediction> getAllPredictions() {
    return predictionService.getAllPredictions();
  }

  @GetMapping("/match/{matchId}")
  @Operation(summary = "Получить прогнозы на матч")
  public List<Prediction> getPredictionsByMatch(@PathVariable String matchId) {
    return predictionService.getPredictionsByMatch(matchId);
  }

  @GetMapping("/user/{userId}")
  @Operation(summary = "Получить прогнозы пользователя")
  public List<Prediction> getPredictionsByUser(@PathVariable String userId) {
    return predictionService.getPredictionsByUser(userId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Создать прогноз")
  public Prediction createPrediction(@Valid @RequestBody Prediction prediction) {
    return predictionService.createPrediction(prediction);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Обновить прогноз")
  public Prediction updatePrediction(@PathVariable String id,
                                     @RequestParam Integer homeScore,
                                     @RequestParam Integer awayScore) {
    return predictionService.updatePrediction(id, homeScore, awayScore);
  }
}
