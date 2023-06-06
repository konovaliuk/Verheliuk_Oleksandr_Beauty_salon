package org.webproject.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webproject.models.Appointment;
import org.webproject.repository.AppointmentRepository;

import java.sql.Connection;
import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    public Appointment getAppointment(long id){
        return appointmentRepository.findById(id).orElse(null);
    }
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    public Appointment createAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }
    public void updateAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }
    public void deleteAppointment(long id){
        appointmentRepository.deleteById(id);
    }
}
