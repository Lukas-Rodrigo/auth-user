package com.lucastexeira.authuser.adapters.out.persistence.repository;


import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BusinessServiceRepository extends
    JpaRepository<BusinessServiceEntity, UUID> {

  @Query("""
                SELECT b FROM BusinessServiceEntity b
                WHERE (:userId IS NULL OR b.user.id = :userId)
                  AND (:startDate IS NULL OR b.createdAt >= :startDate)
                  AND (:endDate IS NULL OR b.createdAt <= :endDate)
      """)
  Page<BusinessServiceEntity> findAllWithFilter(
      UUID userId,
      LocalDate startDate,
      LocalDate endDate,
      Pageable pageable
  );



}
