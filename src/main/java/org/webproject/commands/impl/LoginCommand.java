package org.webproject.commands.impl;

import org.webproject.commands.CommandController;
import org.webproject.models.User;
import org.webproject.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements CommandController {
    @Override
    public boolean execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserService userService = new UserService();
        User newUser = userService.loginUser(login, password);
        if (newUser != null) {
            req.getSession().setAttribute("user", newUser);
            return true;
        }
        return false;
    }
}