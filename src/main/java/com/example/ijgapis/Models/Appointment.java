package com.example.ijgapis.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //generate it Auto
    private Long id;
    //
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String gender;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String country;
    
    @Column(nullable = false)
    private String reason;
    
    @ManyToOne
    @JoinColumn(name = "deposit_range_id", nullable = false)
    private DepositRange depositRange;
    
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;
    
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
} 