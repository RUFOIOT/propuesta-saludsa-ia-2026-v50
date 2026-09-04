package com.saludsa.salescopilot.repository;

import com.saludsa.salescopilot.domain.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, String> {

    List<Interaction> findByLeadIdOrderByTimestampAsc(String leadId);
}
