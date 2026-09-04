package com.saludsa.salescopilot.repository;

import com.saludsa.salescopilot.domain.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, String> {

    List<Lead> findByEstado(Lead.Status estado);

    List<Lead> findByCanalOrigen(Lead.Channel canal);

    long countByEstado(Lead.Status estado);
}
