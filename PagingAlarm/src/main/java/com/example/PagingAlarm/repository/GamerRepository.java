package com.example.PagingAlarm.repository;

import com.example.PagingAlarm.domain.Gamer;
import com.example.PagingAlarm.domain.enumType.Rank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
    Page<Gamer> findByAgeGreaterThanEqualAndAgeLessThanEqualAndRankGreaterThanEqualAndRankLessThanEqual(
            Integer ageGe, Integer ageLe, Rank rankGe, Rank rankLe, Pageable pageable);
}
