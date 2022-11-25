package com.example.csis3275project.repositories;

import com.example.csis3275project.entities.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
}
