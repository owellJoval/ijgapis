package com.example.ijgapis.Repositories;

import com.example.ijgapis.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
} 