package com.example.ijgapis.Repositories;

import com.example.ijgapis.Models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByName(String name);
} 