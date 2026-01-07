package com.lucastexeira.authuser.adapters.out.persistence.repository;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.UUID;

public interface AppointmentRepository extends
    JpaRepository<AppointmentEntity, UUID> {

  @Query("""
          SELECT a FROM AppointmentEntity a
          WHERE (:userId IS NULL OR a.client.user.id = :userId)
            AND (:startDate IS NULL OR a.scheduledAt >= :startDate)
            AND (:endDate IS NULL OR a.scheduledAt <= :endDate)
      """)
  Page<AppointmentEntity> findAllWithFilter(
      UUID userId,
      LocalDate startDate,
      LocalDate endDate,
      Pageable pageable
  );
}
