package org.kakaoPay.jpa.repository;

import org.kakaoPay.jpa.entity.Seeding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SeedingRepository extends JpaRepository<Seeding, String> {
    Seeding findByToken(String token);
    Seeding findByTokenAndCreateDateGreaterThan(String token, LocalDateTime createDate);
}
