package com.example.ijgapis.Controllers;

import com.example.ijgapis.Models.*;
import com.example.ijgapis.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*") // Enable CORS for all origins
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            // Validate required fields
            if (appointment.getFirstName() == null || appointment.getFirstName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("First name is required");
            }
            if (appointment.getLastName() == null || appointment.getLastName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Last name is required");
            }
            if (appointment.getGender() == null || appointment.getGender().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Gender is required");
            }
            if (appointment.getPhone() == null || appointment.getPhone().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Phone number is required");
            }
            if (appointment.getEmail() == null || appointment.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            if (appointment.getCountry() == null || appointment.getCountry().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Country is required");
            }
            if (appointment.getReason() == null || appointment.getReason().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Reason for appointment is required");
            }
            if (appointment.getDepositRange() == null) {
                return ResponseEntity.badRequest().body("Deposit range is required");
            }
            if (appointment.getAppointmentDateTime() == null) {
                return ResponseEntity.badRequest().body("Appointment date and time is required");
            }

            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            return ResponseEntity.ok(createdAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating appointment: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.approveAppointment(id);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error approving appointment: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.rejectAppointment(id);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error rejecting appointment: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching appointments: " + e.getMessage());
        }
    }

    @GetMapping("/deposit-range/{depositRangeId}")
    public ResponseEntity<?> getAppointmentsByDepositRange(@PathVariable Long depositRangeId) {
        try {
            DepositRange depositRange = new DepositRange();
            depositRange.setId(depositRangeId);
            List<Appointment> appointments = appointmentService.getAppointmentsByDepositRange(depositRange);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching appointments by deposit range: " + e.getMessage());
        }
    }
} 