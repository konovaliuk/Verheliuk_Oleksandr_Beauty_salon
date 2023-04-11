package org.webproject.commands.impl;

import org.webproject.commands.CommandController;
import org.webproject.models.Role;
import org.webproject.models.User;
import org.webproject.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class SignUpCommand implements CommandController {
    @Override
    public boolean execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        User user = new User(-1, login, password, firstName, lastName, new ArrayList<Role>());
        User newUser = new UserService().registerUser(user);
        if (newUser != null){
            req.getSession().setAttribute("user", newUser);
            return true;
        }
        return false;
    }
}
