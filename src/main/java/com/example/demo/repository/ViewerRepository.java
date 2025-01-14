package com.example.demo.repository;

import com.example.demo.entity.ViewerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewerRepository extends JpaRepository<ViewerEntity, Integer> {
    Optional<Integer> countByMovieId(int movieId);
}
