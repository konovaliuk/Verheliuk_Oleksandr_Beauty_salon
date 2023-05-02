import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.webproject.dao.ConnectionPool;
import org.webproject.dao.impl.AppointmentDAOImpl;
import org.webproject.dao.impl.RoleDAOImpl;
import org.webproject.dao.impl.UserDAOImpl;
import org.webproject.dao.impl.WorkdayDAOImpl;
import org.webproject.models.Appointment;
import org.webproject.models.Role;
import org.webproject.models.User;
import org.webproject.models.Workday;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BeautySalon");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        User user = new UserDAOImpl(em).get(15);
        Role newRole = new RoleDAOImpl(em).getByName("master");
        user.addRole(newRole);
        new UserDAOImpl(em).update(user);
        System.out.println(user);
        System.out.println("-----------");

        List<Workday> all_workdays= new WorkdayDAOImpl(em).getAll();
        for (Workday workday: all_workdays){
                System.out.println(workday);
        }
        System.out.println("-----------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Workday workday = new Workday();
        workday.setMaster(user);
        workday.setWorkStart(LocalDateTime.parse("2023-05-05 09:00:00", formatter));
        workday.setWorkFinish(LocalDateTime.parse("2023-05-05 18:00:00", formatter));
        new WorkdayDAOImpl(em).create(workday);
        System.out.println("-----------");

        List<Workday> newAllWorkdays= new WorkdayDAOImpl(em).getAll();
        for (Workday newWorkday: newAllWorkdays){
            System.out.println(newWorkday);
        }
        System.out.println("-----------");

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
