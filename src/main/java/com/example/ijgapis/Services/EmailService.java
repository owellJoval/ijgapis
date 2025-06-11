package com.example.ijgapis.Services;

import com.example.ijgapis.Models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendAppointmentApprovalEmail(Appointment appointment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(appointment.getEmail());
        message.setSubject("Appointment Approved");
        
        String content = String.format(
            "Good day %s %s,\n\nYour appointment has been approved for %s.\n\nBest regards,\nIJG Team",
            appointment.getFirstName(),
            appointment.getLastName(),
            appointment.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
        
        message.setText(content);
        emailSender.send(message);
    }

    public void sendAppointmentRejectionEmail(Appointment appointment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(appointment.getEmail());
        message.setSubject("Appointment Not Available");
        
        String content = String.format(
            "Good day %s %s,\n\nWe regret to inform you that your requested appointment time (%s) is not available. " +
            "Please visit our booking form to select a different time slot.\n\nBest regards,\nIJG Team",
            appointment.getFirstName(),
            appointment.getLastName(),
            appointment.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
        
        message.setText(content);
        emailSender.send(message);
    }
} 