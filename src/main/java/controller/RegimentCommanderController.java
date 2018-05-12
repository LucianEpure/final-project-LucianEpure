package controller;


import dto.ScheduleDto;
import dto.UserDto;
import entity.Schedule;
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
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
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
        public String schedule(Model model, Principal principal, HttpSession session){
    ScheduleDto scheduleDto = new ScheduleDto();
    scheduleDto.setDate(new Date());
    String username = principal.getName();
    String regimentCode = username.replaceAll("[^0-9]","");
    scheduleDto = scheduleService.save(scheduleDto,Integer.parseInt(regimentCode));
    session.setAttribute("scheduleDto",scheduleDto);
    return "redirect:/regimentCommander/schedule";
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
