package com.lucastexeira.authuser.adapters.out.persistence.repository;

import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

  boolean existsByIdAndUserId(UUID id, UUID userId);

  @Query(
      """
          SELECT c FROM ClientEntity c
          WHERE (:userId IS NULL OR c.user.id = :userId)
            AND (:startDate IS NULL OR c.createdAt >= :startDate)
            AND (:endDate IS NULL OR c.createdAt <= :endDate)
      """
  )
  Page<ClientEntity> findAllWithFilter(
      UUID userId,
      LocalDate startDate,
      LocalDate endDate,
      Pageable pageable
  );

  ClientEntity findClientEntitiesByPhoneNumberAndNameAndUserId(
      String phoneNumber,
      String name,
      UUID user_id
  );

  void deleteByIdAndUserId(UUID id, UUID userId);
}
