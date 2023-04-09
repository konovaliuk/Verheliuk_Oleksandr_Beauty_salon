package org.webproject.dao.impl;

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

    private static final String SQL_SELECT_APPOINTMENT_BY_ID = "SELECT * FROM appointments WHERE id = ?";
    private static final String SQL_SELECT_ALL_APPOINTMENTS = "SELECT * FROM appointments";
    private static final String SQL_ADD_APPOINTMENT = "INSERT INTO appointments (id_workday, id_customer, " +
            "appointments_start, appointments_finish, feedback) VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_APPOINTMENT = "UPDATE appointments SET id_workday = ?, id_customer = ?, " +
            "appointments_start = ?, appointments_finish = ?, feedback = ? WHERE id = ?";
    private static final String SQL_DELETE_APPOINTMENT = "DELETE FROM appointments WHERE id = ?";
    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public AppointmentDAOImpl(Connection con){
        this.con = con;
    }

    @Override
    public Appointment get(long id){
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_APPOINTMENT_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    return getAppointment(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find appointment by id " + id + ". " + ex.getMessage());
        }
        return null;
    }

    private Appointment getAppointment(ResultSet rs){
        try {
            long id = rs.getLong("id");
            long id_workday = rs.getLong("id_workday");
            long id_customer = rs.getLong("id_customer");
            LocalDateTime appointments_start = rs.getObject("appointments_start", LocalDateTime.class);
            LocalDateTime appointments_finish = rs.getObject("appointments_finish", LocalDateTime.class);
            String feedback = rs.getString("feedback");
            Workday workday = new WorkdayDAOImpl(con).get(id_workday);
            User customer = new UserDAOImpl(con).get(id_customer);
            return new Appointment(id, workday, customer, appointments_start, appointments_finish, feedback);
        } catch (SQLException ex) {
            logger.error("Error. Can't get Appointment." + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Appointment> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_APPOINTMENTS)) {
            List<Appointment> appointments = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Appointment appointment = getAppointment(rs);
                    appointments.add(appointment);
                }
                return appointments;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get all appointments. " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Appointment create(Appointment appointment) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_APPOINTMENT, PreparedStatement.RETURN_GENERATED_KEYS)){
            setAppointment(stmt, appointment);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    appointment.setAppointmentId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't add new appointment. " + ex.getMessage());
        }
        return appointment;
    }

    private void setAppointment(PreparedStatement stmt, Appointment appointment) throws SQLException {
        stmt.setLong(1, appointment.getWorkday().getIdWorkday());
        stmt.setLong(2, appointment.getCustomer().getUserId());
        stmt.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentStart()));
        stmt.setTimestamp(4, Timestamp.valueOf(appointment.getAppointmentFinish()));
        stmt.setString(5, appointment.getFeedback());
    }

    @Override
    public void update(Appointment appointment) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_APPOINTMENT)){
            stmt.setLong(6, appointment.getAppointmentId());
            setAppointment(stmt, appointment);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't update appointment. " + ex.getMessage());
        }
    }

    @Override
    public void delete (long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_APPOINTMENT)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete appointment. " + ex.getMessage());
        }
    }

}
