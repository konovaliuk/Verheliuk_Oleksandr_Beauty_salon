import org.webproject.dao.ConnectionPool;
import org.webproject.dao.impl.AppointmentDAOImpl;
import org.webproject.dao.impl.RoleDAOImpl;
import org.webproject.dao.impl.UserDAOImpl;
import org.webproject.dao.impl.WorkdayDAOImpl;
import org.webproject.models.Appointment;
import org.webproject.models.Role;
import org.webproject.models.User;
import org.webproject.models.Workday;
import org.webproject.services.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection con = ConnectionPool.getConnection()) {
            UserService user = new UserService();
//            System.out.println(user.getHash("1"));
//            List<Role> all_roles =  new RoleDAOImpl(con).getAll();
//            for (Role role: all_roles){
//                System.out.println(role);
//            }
//
//
//            User newUser = new User(-1, "taras2@ukrnet.com", "12345", "Taras", "Shevchenko", new ArrayList<Role>());
//            new UserDAOImpl(con).create(newUser);
//            Role newRole = new RoleDAOImpl(con).get(1);
//            Role newRole2 = new RoleDAOImpl(con).get(2);
//            new UserDAOImpl(con).addUserRole(newUser, newRole);
//            new UserDAOImpl(con).addUserRole(newUser, newRole2);
//
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            Workday workday = new Workday(-1, newUser, LocalDateTime.parse("2023-03-23 09:00:00", formatter), LocalDateTime.parse("2023-03-23 18:00:00", formatter));
//            new WorkdayDAOImpl(con).create(workday);
//
//            User newCustomer = new User(-1, "ivan2@ukrnet.com", "12345", "Ivan", "Franko", new ArrayList<Role>());
//            new UserDAOImpl(con).create(newCustomer);
//            Appointment appointment = new Appointment(-1, workday, newCustomer, LocalDateTime.parse("2023-03-23 10:00:00", formatter), LocalDateTime.parse("2023-03-23 11:00:00", formatter), "");
//            new AppointmentDAOImpl(con).create(appointment);
//            newCustomer.setPassword("67890");
//            new UserDAOImpl(con).update(newCustomer);
        } catch (SQLException ex) {
            System.out.println("Error. Can't connection to database. " + ex.getMessage());
        } finally {
            ConnectionPool.closePool();
        }
    }
}
