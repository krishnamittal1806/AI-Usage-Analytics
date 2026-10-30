package com.krishna.analytics.consumer.repository;

import com.krishna.analytics.consumer.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {}