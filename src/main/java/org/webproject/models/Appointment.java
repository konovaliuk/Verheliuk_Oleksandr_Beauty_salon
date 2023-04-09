package org.webproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Appointment {

    private long appointmentId;

    private Workday workday;

    private User customer;

    private LocalDateTime appointmentStart;

    private LocalDateTime appointmentFinish;

    private String feedback;
}