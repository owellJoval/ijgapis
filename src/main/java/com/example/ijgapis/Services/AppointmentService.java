package com.example.ijgapis.Services;

import com.example.ijgapis.Models.*;
import com.example.ijgapis.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmailService emailService;

    public Appointment createAppointment(Appointment appointment) {
        Status pendingStatus = statusRepository.findByName("PENDING");
        appointment.setStatus(pendingStatus);
        return appointmentRepository.save(appointment);
    }

    public Appointment approveAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Status approvedStatus = statusRepository.findByName("APPROVED");
        appointment.setStatus(approvedStatus);
        appointment = appointmentRepository.save(appointment);

        // Create event
        Event event = new Event();
        event.setAppointment(appointment);
        event.setTitle("Appointment with " + appointment.getFirstName() + " " + appointment.getLastName());
        event.setStartTime(appointment.getAppointmentDateTime());
        event.setEndTime(appointment.getAppointmentDateTime().plusHours(1));
        event.setDescription("Appointment for " + appointment.getReason());
        eventRepository.save(event);

        // Send approval email
        emailService.sendAppointmentApprovalEmail(appointment);

        return appointment;
    }

    public Appointment rejectAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Status rejectedStatus = statusRepository.findByName("REJECTED");
        appointment.setStatus(rejectedStatus);
        appointment = appointmentRepository.save(appointment);

        // Send rejection email
        emailService.sendAppointmentRejectionEmail(appointment);

        return appointment;
    }

    public List<Appointment> getAppointmentsByDepositRange(DepositRange depositRange) {
        return appointmentRepository.findByDepositRange(depositRange);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
} 