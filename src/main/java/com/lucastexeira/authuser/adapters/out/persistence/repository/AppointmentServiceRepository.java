package com.lucastexeira.authuser.adapters.out.persistence.repository;

import com.lucastexeira.authuser.adapters.out.persistence.entity.association.AppointmentServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentServiceRepository extends
    JpaRepository<AppointmentServiceEntity, UUID> {
}
