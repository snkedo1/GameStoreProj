package com.example.demoapp.repo;

import com.example.demoapp.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepo extends JpaRepository<Genre,Long> {
    Optional<Genre> findByNameIs(String name);
}
