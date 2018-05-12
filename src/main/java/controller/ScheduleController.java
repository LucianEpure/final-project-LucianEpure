package controller;

import dto.ActivityDto;
import dto.RegimentDto;
import dto.ScheduleDto;
import dto.ScheduleReport;
import entity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/regimentCommander/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }
    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model, HttpSession session) {

        ScheduleDto scheduleDto = (ScheduleDto) session.getAttribute("scheduleDto");
        ScheduleReport scheduleReport = scheduleDto.getScheduleReport();
        List<ActivityDto> activities = scheduleDto.getActivities();
        model.addAttribute("activities",activities);
        model.addAttribute("scheduleReport",scheduleReport);

        return "scheduler";
    }

    @PostMapping(params = "addActivity")
    public String addActivity( Model model, @RequestParam("type") String type, HttpSession session){
        ActivityDto activityDto = scheduleService.findActivityByName(type);
        ScheduleDto scheduleDto = (ScheduleDto) session.getAttribute("scheduleDto");
        scheduleService.addActivity(scheduleDto,activityDto);
        return "redirect:/regimentCommander/schedule";
    }

    @PostMapping(params = "removeActivity")
    public String removeActivity( Model model, HttpSession session){
        ScheduleDto scheduleDto = (ScheduleDto) session.getAttribute("scheduleDto");
        scheduleService.removeActivity(scheduleDto);
        return "redirect:/regimentCommander/schedule";
    }

    @PostMapping(params = "finishSchedule")
    public String finishSchedule( HttpSession session){
        ScheduleDto scheduleDto = (ScheduleDto) session.getAttribute("scheduleDto");
        scheduleService.update(scheduleDto);
        return "redirect:/regimentCommander";
    }

    @PostMapping(params="logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

}

