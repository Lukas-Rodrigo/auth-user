package com.lucastexeira.authuser.adapters.out.persistence.repository;

import com.lucastexeira.authuser.adapters.out.persistence.entity.whatsapp.WhatsappSessionEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WhatsappSessionRepository extends JpaRepository<WhatsappSessionEntity, UUID> {

  Optional<WhatsappSessionEntity> findByUserId(UUID userId);
}
