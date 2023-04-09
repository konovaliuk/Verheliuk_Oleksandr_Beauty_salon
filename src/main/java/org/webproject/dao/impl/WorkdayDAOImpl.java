package org.webproject.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webproject.dao.WorkdayDAO;
import org.webproject.models.Appointment;
import org.webproject.models.User;
import org.webproject.models.Workday;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkdayDAOImpl implements WorkdayDAO {

    private static final String SQL_SELECT_WORKDAY_BY_ID = "SELECT * FROM workdays WHERE id = ?";
    private static final String SQL_SELECT_ALL_WORKDAYS = "SELECT * FROM workdays";
    private static final String SQL_ADD_WORKDAY = "INSERT INTO workdays (id_master, work_start, work_finish) VALUES (?, ? , ?)";
    private static final String SQL_UPDATE_WORKDAY = "UPDATE workdays SET id_master = ?, work_start = ?, work_finish = ?," +
            "WHERE id = ?";
    private static final String SQL_DELETE_WORKDAY = "DELETE FROM workdays WHERE id = ?";
    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public WorkdayDAOImpl(Connection con){
        this.con = con;
    }

    @Override
    public Workday get(long id){
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_WORKDAY_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    return getWorkday(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find workday by id " + id + ". " + ex.getMessage());
        }
        return null;
    }

    private Workday getWorkday(ResultSet rs) {
        try {
            long id = rs.getLong("id");
            long id_master = rs.getLong("id_master");
            LocalDateTime work_start = rs.getObject("work_start", LocalDateTime.class);
            LocalDateTime work_finish = rs.getObject("work_finish", LocalDateTime.class);
            User master = new UserDAOImpl(con).get(id_master);
            return new Workday(id, master, work_start, work_finish);
        } catch (SQLException ex) {
            logger.error("Error. Can't get Workday." + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Workday> getAll(){
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_WORKDAYS)) {
            List<Workday> workdays = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Workday workday = getWorkday(rs);
                    workdays.add(workday);
                }
                return workdays;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get all workdays. " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Workday create(Workday workday) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_WORKDAY, PreparedStatement.RETURN_GENERATED_KEYS)){
            setWorkday(stmt, workday);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    workday.setIdWorkday(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't add new workday. " + ex.getMessage());
        }
        return workday;
    }

    private void setWorkday(PreparedStatement stmt, Workday workday) throws SQLException {
        stmt.setLong(1, workday.getMaster().getUserId());
        stmt.setTimestamp(2, Timestamp.valueOf(workday.getWorkStart()));
        stmt.setTimestamp(3, Timestamp.valueOf(workday.getWorkFinish()));
    }

    @Override
    public void update(Workday workday){
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_WORKDAY)){
            stmt.setLong(4, workday.getIdWorkday());
            setWorkday(stmt, workday);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't update workday. " + ex.getMessage());
        }
    }

    @Override
    public void delete(long id){
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_WORKDAY)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete workday. " + ex.getMessage());
        }
    }

}
