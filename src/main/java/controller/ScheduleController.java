package controller;

import dto.*;
import service.notify.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.regiment.RegimentService;
import service.regiment.RequirementService;
import service.schedule.ScheduleCRUDService;
import service.schedule.ScheduleService;
import service.regiment.UserService;
import service.notify.NotifyService;
import validators.Notification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

import static application.Constants.QUARTERMASTER;

@Controller
@RequestMapping(value = "/regimentCommander/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private ScheduleCRUDService scheduleCRUDService;
    private RegimentService regimentService;
    private RequirementService requirementService;
    private NotifyService notifyService;

    private UserService userService;

    @Autowired
    public ScheduleController(ScheduleCRUDService scheduleCRUDService, ScheduleService scheduleService, RegimentService regimentService, RequirementService requirementService,NotifyService notifyService,UserService userService){
        this.regimentService = regimentService;
        this.scheduleService = scheduleService;
        this.requirementService = requirementService;
        this.userService =userService;
        this.notifyService = notifyService;
        this.scheduleCRUDService = scheduleCRUDService;
    }
    @GetMapping
    @Order(value = 1)
    public String displayMenu(Principal principal, Model model, HttpSession session) {

        ScheduleDto scheduleDto = (ScheduleDto) session.getAttribute("scheduleDto");
        String valid = (String) session.getAttribute("valid");
        ScheduleReportDto scheduleReport = scheduleDto.getScheduleReport();
        List<ActivityDto> activities = scheduleDto.getActivities();
        String username = principal.getName();
        int regimentCode = regimentService.extractRegimentFromUser(username);
        RegimentDto regimentDto = regimentService.findByCode(regimentCode);
        RequirementDto requirementDto = requirementService.findRequirement(regimentDto.getRequirementId());
        model.addAttribute("requirementDto",requirementDto);
        model.addAttribute("valid",valid);
        model.addAttribute("activities",activities);
        model.addAttribute("scheduleReport",scheduleReport);

        return "scheduler";
    }

    @PostMapping(params = "addActivity")
    public String addActivity( @RequestParam("type") String type, HttpSession session){
        ActivityDto activityDto = scheduleService.findActivityByName(type);
        ScheduleDto scheduleDto = (ScheduleDto) session.getAttribute("scheduleDto");
        scheduleService.addActivity(scheduleDto,activityDto);
        session.setAttribute("valid", "");
        return "redirect:/regimentCommander/schedule";
    }

    @PostMapping(params = "removeActivity")
    public String removeActivity( Model model, HttpSession session){
        ScheduleDto scheduleDto = (ScheduleDto) session.getAttribute("scheduleDto");
        scheduleService.removeActivity(scheduleDto);
        session.setAttribute("valid", "");
        return "redirect:/regimentCommander/schedule";
    }

    @PostMapping(params = "finishSchedule")
    public String finishSchedule(@ModelAttribute ScheduleReportDto scheduleRep, Principal principal, HttpSession session, Model model){
        ScheduleDto scheduleDto = (ScheduleDto) session.getAttribute("scheduleDto");
        Notification notification = scheduleCRUDService.update(scheduleDto);
        if(notification.hasErrors()) {
            session.setAttribute("valid", notification.getFormattedErrors());
            return "redirect:/regimentCommander/schedule";
        }
        else{
            Message message = new Message();
            message.setContent(principal.getName()+" added a schedule!.");
            UserDto userDto = userService.findAdmin();
            notifyService.notify(userDto.getUsername(),message);
            return "redirect:/regimentCommander";
        }


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

