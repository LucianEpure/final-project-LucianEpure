package controller;


import dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.schedule.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/regimentCommander")
public class RegimentCommanderController {

    private ScheduleService scheduleService;

    @Autowired
    public RegimentCommanderController(ScheduleService scheduleService){
         this.scheduleService = scheduleService;
    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model){

        return "regimentCommander";
    }

    @PostMapping(value = "/schedule", params = "schedule")
        public String schedule(Principal principal, HttpSession session){
        boolean approved;
        ScheduleDto scheduleDto = new ScheduleDto();
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date today = new Date();
            Date todayWithZeroTime = formatter.parse(formatter.format(today));
            scheduleDto.setDate(todayWithZeroTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String username = principal.getName();
        String regimentCode = username.replaceAll("[^0-9]","");
        scheduleDto.setRegimentCode(Integer.parseInt(regimentCode));
        approved = scheduleService.checkIfApproved(scheduleDto);
        if(approved)
            return "scheduleError";
        else{
            scheduleDto = scheduleService.save(scheduleDto);
            session.setAttribute("scheduleDto",scheduleDto);
            return "redirect:/regimentCommander/schedule";
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
