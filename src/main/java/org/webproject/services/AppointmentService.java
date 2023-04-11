package org.webproject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webproject.dao.AppointmentDAO;
import org.webproject.dao.ConnectionPool;
import org.webproject.dao.impl.AppointmentDAOImpl;
import org.webproject.models.Appointment;

import java.sql.Connection;
import java.util.List;

public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public Appointment getAppointment(long id){
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                return appointmentDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    public List<Appointment> getAllAppointments() {
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                return appointmentDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    public Appointment createAppointment(Appointment appointment){
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                con.setAutoCommit(false);
                if (appointmentDAO.create(appointment) != null) {
                    con.commit();
                    return appointment;
                }
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    public void updateAppointment(Appointment appointment){
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                con.setAutoCommit(false);
                appointmentDAO.update(appointment);
                con.commit();
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
    }
    public boolean deleteAppointment(long id){
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                con.setAutoCommit(false);
                appointmentDAO.delete(id);
                con.commit();
                return true;
            } catch (Exception ex){
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return false;
    }
}
