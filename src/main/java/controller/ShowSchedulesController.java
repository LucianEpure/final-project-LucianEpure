package controller;

import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.notify.Message;
import service.notify.NotifyService;
import service.regiment.RegimentService;
import service.regiment.SupplyService;
import service.regiment.UserService;
import service.schedule.ScheduleCRUDService;
import service.schedule.ScheduleReportService;
import service.schedule.ScheduleService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import static application.Constants.APPROVAL;
import static application.Constants.REGIMENTCOMMANDER;

@Controller
@RequestMapping(value = "/quartermaster/showSchedules")
public class ShowSchedulesController {


    private ScheduleService scheduleService;
    private NotifyService notifyService;
    private UserService userService;
    private ScheduleCRUDService scheduleCRUDService;

    @Autowired
    public ShowSchedulesController(ScheduleCRUDService scheduleCRUDService,ScheduleService scheduleService, ScheduleReportService scheduleReportService,SupplyService supplyService, RegimentService regimentService, SimpMessagingTemplate messagingTemplate, UserService userService, NotifyService notifyService){
    this.scheduleService = scheduleService;
    this.userService = userService;
    this.notifyService= notifyService;
    this.scheduleCRUDService = scheduleCRUDService;
    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model,HttpSession session){
        Date date = (Date) session.getAttribute("scheduleDate");
        List<ScheduleDto> scheduleDtos = scheduleService.findByDate(date);
        model.addAttribute("schedules", scheduleDtos);
        return "scheduleView";
    }

    @PostMapping(value = "/{scheduleId}",params = "approve")
    public String approve(@PathVariable(value = "scheduleId") int scheduleId,Principal principal) {
        ScheduleDto scheduleDto = scheduleCRUDService.findById(scheduleId);
        scheduleService.approveSchedule(scheduleDto);
        Message message = new Message();
        message.setContent(principal.getName()+APPROVAL+scheduleId);
        UserDto user  = userService.findUser(scheduleDto.getRegimentCode());
        notifyService.notify(user.getUsername(),message);
        return "redirect:/quartermaster/showSchedules";
    }
    @PostMapping(value = "/{scheduleId}", params = "deny")
    public String deny(@PathVariable(value = "scheduleId") int scheduleId,Model model){

       scheduleService.denySchedule(scheduleId);
        return "redirect:/quartermaster/showSchedules";
    }
}
