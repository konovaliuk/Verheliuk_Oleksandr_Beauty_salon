package org.webproject.dao.impl;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webproject.dao.AppointmentDAO;
import org.webproject.models.Appointment;
import org.webproject.models.User;
import org.webproject.models.Workday;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {

    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentDAOImpl.class);
    public AppointmentDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public Appointment get(long id){
        return em.find(Appointment.class, id);
    }

    @Override
    public List<Appointment> getAll() {
        return em.createQuery("SELECT a FROM Appointment a", Appointment.class).getResultList();
    }

    @Override
    public Appointment create(Appointment appointment) {
        em.persist(appointment);
        return appointment;
    }

    @Override
    public void update(Appointment appointment) {
        em.merge(appointment);
    }

    @Override
    public void delete (long id) {
        em.remove(em.find(Appointment.class, id));
    }

}
