package org.webproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long appointmentId;

    @ManyToOne
    @JoinColumn(name = "id_workday")
    private Workday workday;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private User customer;

    @Column(name = "appointments_start")
    private LocalDateTime appointmentStart;

    @Column(name = "appointments_finish")
    private LocalDateTime appointmentFinish;

    private String feedback;
}