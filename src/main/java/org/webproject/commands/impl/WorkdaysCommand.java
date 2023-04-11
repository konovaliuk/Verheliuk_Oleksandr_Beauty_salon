package org.webproject.commands.impl;

import org.webproject.commands.CommandController;
import org.webproject.models.Workday;
import org.webproject.services.WorkdayService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class WorkdaysCommand implements CommandController {

    @Override
    public boolean execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            WorkdayService workdayService = new WorkdayService();
            List<Workday> workdays = workdayService.getAllWorkdays();
            req.getSession().setAttribute("workdays", workdays);
            req.getRequestDispatcher("/html/schedule.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
