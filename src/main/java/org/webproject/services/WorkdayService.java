package org.webproject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webproject.dao.ConnectionPool;
import org.webproject.dao.WorkdayDAO;
import org.webproject.dao.impl.WorkdayDAOImpl;
import org.webproject.models.Workday;

import java.sql.Connection;
import java.util.List;

public class WorkdayService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public Workday getWorkday(long id){
        try (Connection con = ConnectionPool.getConnection()){
            WorkdayDAO workdayDAO = new WorkdayDAOImpl(con);
            try {
                return workdayDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    public List<Workday> getAllWorkdays(){
        try (Connection con = ConnectionPool.getConnection()){
            WorkdayDAO workdayDAO = new WorkdayDAOImpl(con);
            try {
                return workdayDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    public Workday createWorkday(Workday workday){
        try (Connection con = ConnectionPool.getConnection()){
            WorkdayDAO workdayDAO = new WorkdayDAOImpl(con);
            try {
                con.setAutoCommit(false);
                if (workdayDAO.create(workday) != null) {
                    con.commit();
                    return workday;
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
    public void updateWorkday(Workday workday){
        try (Connection con = ConnectionPool.getConnection()){
            WorkdayDAO workdayDAO = new WorkdayDAOImpl(con);
            try {
                con.setAutoCommit(false);
                workdayDAO.update(workday);
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
    public boolean deleteWorkday(long id){
        try (Connection con = ConnectionPool.getConnection()){
            WorkdayDAO workdayDAO = new WorkdayDAOImpl(con);
            try {
                con.setAutoCommit(false);
                workdayDAO.delete(id);
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
