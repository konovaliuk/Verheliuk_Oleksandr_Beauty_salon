package org.webproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.webproject.models.Workday;
import org.webproject.services.WorkdayService;

import java.util.List;

@Controller
public class ScheduleController {
    private final WorkdayService workdayService;

    @Autowired
    public ScheduleController(WorkdayService workdayService) {
        this.workdayService = workdayService;
    }
    @RequestMapping("/html/schedule/{page}")
    public String workdayList(@PathVariable int page, Model model){
        int pageSize = 2;


        Page<Workday> pageWorkday = workdayService.getAllWorkdays(page, pageSize);
        List<Workday> listWorkday = pageWorkday.getContent();
        model.addAttribute("listWorkday", listWorkday);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", pageWorkday.getTotalPages());
        model.addAttribute("startPage", Math.max(1, page - 2));
        model.addAttribute("endPage", Math.min(pageWorkday.getTotalPages(), page + 2));
        return "schedule";
    }
}
