package org.webproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.webproject.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
