package com.football_prophency.championship_artefact.repository;

import com.football_prophency.championship_artefact.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

  List<User> findAllByOrderByTotalPointsDesc();
}
