package org.webproject.dao.impl;

import jakarta.persistence.EntityManager;
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

    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(WorkdayDAOImpl.class);
    public WorkdayDAOImpl(EntityManager em){
        this.em = em;
    }


    @Override
    public Workday get(long id){
        return em.find(Workday.class, id);
    }

    @Override
    public List<Workday> getAll(){
        return em.createQuery("SELECT w FROM Workday w", Workday.class).getResultList();
    }

    @Override
    public Workday create(Workday workday) {
        em.persist(workday);
        return workday;
    }

    @Override
    public void update(Workday workday){
        em.merge(workday);
    }

    @Override
    public void delete(long id){
        em.remove(em.find(Workday.class, id));
    }

}
