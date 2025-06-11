package com.example.ijgapis.Repositories;

import com.example.ijgapis.Models.Appointment;
import com.example.ijgapis.Models.DepositRange;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDepositRange(DepositRange depositRange);
} 